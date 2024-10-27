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
    private final Sql2o sql2o;
    public ProductRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public List<ProductEntity> findAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM products")
                    .executeAndFetch(ProductEntity.class);
        }
    }

    public ProductEntity findById(long id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM products WHERE id=:id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(ProductEntity.class);
        }
    }

    public ProductEntity save(ProductEntity product) {
        try (Connection con = sql2o.open()) {
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
            product.setId(id);
            return product;
        }
    }

    public ProductEntity update(long id, ProductEntity product) {
        try (Connection con = sql2o.open()) {
            String query = "UPDATE products " +
                    "SET stock =:stock " +
                    "WHERE id = :id";
            con.createQuery(query)
                    .addParameter("stock", product.getStock())
                    .addParameter("id", id)
                    .executeUpdate();

            ProductEntity updatedProduct = con.createQuery("SELECT * FROM products WHERE id = :id")
                    .addParameter("id", product.getId())
                    .executeAndFetchFirst(ProductEntity.class);
            return updatedProduct;
        }

    }

    public boolean softDelete(long id) {
        String query = "UPDATE products SET deleted_at = :deletedAt WHERE id = :id AND deleted_at IS NULL";
        try (Connection con = sql2o.open()) {
            int rowsSoftDeleted = con.createQuery(query)
                    .addParameter("deletedAt", LocalDateTime.now())
                    .addParameter("id", id)
                    .executeUpdate()
                    .getResult();
            return rowsSoftDeleted > 0;
        }

    }
}
