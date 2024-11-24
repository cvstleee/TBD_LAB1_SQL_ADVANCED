package com.example.backend.repositories;

import com.example.backend.entities.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ProductRepository {

    @Autowired
    private Sql2o sql2o;

    public List<ProductEntity> findAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM products WHERE deleted_at IS NULL and state='available'")
                    .executeAndFetch(ProductEntity.class);
        }
    }

    public ProductEntity findById(long id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM products WHERE id=:id AND deleted_at IS NULL")
                    .addParameter("id", id)
                    .executeAndFetchFirst(ProductEntity.class);
        }
    }

    public ProductEntity save(ProductEntity product, int clientId) {
        try (Connection con = sql2o.beginTransaction()) {
            con.createQuery("SET LOCAL application.client_id = " + clientId).executeUpdate();

            String query = "INSERT INTO products (name, description, price, stock, state, category_id) " +
                    "VALUES (:name, :description, :price, :stock, :state, :category_id) RETURNING id";
            int id = con.createQuery(query, true)
                    .addParameter("name", product.getName())
                    .addParameter("description", product.getDescription())
                    .addParameter("price", product.getPrice())
                    .addParameter("stock", product.getStock())
                    .addParameter("state", product.getState())
                    .addParameter("category_id", product.getCategory_id())
                    .executeUpdate()
                    .getKey(Integer.class);
            con.commit();
            product.setId(id);
            return product;
        }
    }

    public ProductEntity update(int id, ProductEntity product, int clientId) {
        try (Connection con = sql2o.beginTransaction()) {
            con.createQuery("SET LOCAL application.client_id = " + clientId).executeUpdate();

            String query = """
            CALL update_product(
                CAST(:p_id AS INT),
                CAST(:p_name AS VARCHAR),
                CAST(:p_description AS TEXT),
                CAST(:p_price AS DECIMAL),
                CAST(:p_stock AS INT),
                CAST(:p_state AS VARCHAR),
                CAST(:p_deleted_at AS TIMESTAMP)
            )
        """;
            con.createQuery(query)
                    .addParameter("p_id", id)
                    .addParameter("p_name", product.getName())
                    .addParameter("p_description", product.getDescription())
                    .addParameter("p_price", product.getPrice())
                    .addParameter("p_stock", product.getStock())
                    .addParameter("p_state", product.getState())
                    .addParameter("p_deleted_at", product.getDeleted_at() != null ? product.getDeleted_at() : null)
                    .executeUpdate();

            ProductEntity updatedProduct = con.createQuery("SELECT * FROM products WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(ProductEntity.class);

            con.commit();

            return updatedProduct;
        }
    }

    public boolean delete(long id, int clientId) {
        String query = "UPDATE products SET deleted_at = :deletedAt WHERE id = :id AND deleted_at IS NULL";
        try (Connection con = sql2o.beginTransaction()) {
            con.createQuery("SET LOCAL application.client_id = " + clientId).executeUpdate();

            int rowsSoftDeleted = con.createQuery(query)
                    .addParameter("deletedAt", LocalDateTime.now())
                    .addParameter("id", id)
                    .executeUpdate()
                    .getResult();
            con.commit();
            return rowsSoftDeleted > 0;
        }
    }

    public List<ProductEntity> updateProductsPriceWithRateByCategory(double rate, int categoryId) {
        try (Connection con = sql2o.open()) {
            con.createQuery("CALL update_products_prices_with_rate(CAST(:rate AS NUMERIC), CAST(:category_id AS INT))")
                    .addParameter("rate", rate)
                    .addParameter("category_id", categoryId)
                    .executeUpdate();

            return con.createQuery("SELECT * FROM products WHERE category_id = :category_id")
                    .addParameter("category_id", categoryId)
                    .executeAndFetch(ProductEntity.class);
        }
    }
}
