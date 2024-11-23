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
            return con.createQuery("SELECT * FROM products WHERE deleted_at IS NULL")
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

    public ProductEntity update(long id, ProductEntity product, int clientId) {
        try (Connection con = sql2o.beginTransaction()) {
            con.createQuery("SET LOCAL application.client_id = " + clientId).executeUpdate();

            String query = "UPDATE products SET stock =:stock WHERE id = :id";
            con.createQuery(query)
                    .addParameter("stock", product.getStock())
                    .addParameter("id", id)
                    .executeUpdate();
            con.commit();

            ProductEntity updatedProduct = con.createQuery("SELECT * FROM products WHERE id = :id")
                    .addParameter("id", product.getId())
                    .executeAndFetchFirst(ProductEntity.class);
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
}
