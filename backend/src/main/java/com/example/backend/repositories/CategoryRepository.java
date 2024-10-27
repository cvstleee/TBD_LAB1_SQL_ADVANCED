package com.example.backend.repositories;

import com.example.backend.entities.CategoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class CategoryRepository {

    @Autowired
    private final Sql2o sql2o;

    public CategoryRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public CategoryEntity findById(long id) {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT * FROM categories WHERE id = :id";
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(CategoryEntity.class);
        }
    }
    public List<CategoryEntity> findAll() {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT * FROM categories";
            return con.createQuery(sql)
                    .executeAndFetch(CategoryEntity.class);
        }
    }

    public CategoryEntity save(CategoryEntity category) {
        try (Connection con = sql2o.open()) {
            String sql = "INSERT INTO categories (name) VALUES (:name)";
            int id = con.createQuery(sql, true)
                    .addParameter("name", category.getName())
                    .executeUpdate()
                    .getKey(Integer.class);
            category.setId(id);
            return category;
        }
    }

    public CategoryEntity update(long id, CategoryEntity category) {
        try (Connection con = sql2o.open()) {
            String sql = "UPDATE categories SET name = :name WHERE id = :id";
            con.createQuery(sql)
                    .addParameter("name", category.getName())
                    .addParameter("id", id)
                    .executeUpdate();
            CategoryEntity updatedCategory = con.createQuery("SELECT * FROM categories WHERE id = :id")
                    .addParameter("id", category.getId())
                    .executeAndFetchFirst(CategoryEntity.class);

            return updatedCategory;
        }
    }

    public boolean softDelete (long id) {
        String query = "UPDATE categories SET deleted_at = :deletedAt WHERE id = :id AND deleted_at IS NULL";
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
