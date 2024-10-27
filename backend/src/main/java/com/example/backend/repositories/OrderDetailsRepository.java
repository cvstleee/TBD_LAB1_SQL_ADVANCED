package com.example.backend.repositories;

import com.example.backend.entities.OrderDetailsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class OrderDetailsRepository {

    @Autowired
    private final Sql2o sql2o;
    public OrderDetailsRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public List<OrderDetailsEntity> findAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM order_details")
                    .executeAndFetch(OrderDetailsEntity.class);
        }
    }

    public OrderDetailsEntity findById(long id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM order_details WHERE id =:id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(OrderDetailsEntity.class);
        }
    }

    public OrderDetailsEntity save(OrderDetailsEntity orderDetails) {
        try (Connection con = sql2o.open()) {
            String query = "INSERT INTO order_details (order_id, product_id, quantity, unit_price) " +
                    "VALUES (:order_id, :product_id, :quantity, :unit_price) RETURNING id";
            int id = con.createQuery(query, true)
                    .addParameter("order_id", orderDetails.getOrder_id())
                    .addParameter("product_id", orderDetails.getProduct_id())
                    .addParameter("quantity", orderDetails.getQuantity())
                    .addParameter("unit_price", orderDetails.getUnit_price())
                    .executeUpdate()
                    .getKey(Integer.class);
            orderDetails.setId(id);
            return orderDetails;
        }
    }

    public OrderDetailsEntity update(long id, OrderDetailsEntity orderDetails) {
        try (Connection con = sql2o.open()) {
            String query = "UPDATE order_details " +
                    "SET quantity =:quantity " +
                    "WHERE id =:id";
            con.createQuery(query)
                    .addParameter("quantity", orderDetails.getQuantity())
                    .addParameter("id", id)
                    .executeUpdate();

            OrderDetailsEntity updatedOrderDetails = con.createQuery("SELECT * FROM order_details WHERE id =:id")
                    .addParameter("id", orderDetails.getId())
                    .executeAndFetchFirst(OrderDetailsEntity.class);
            return updatedOrderDetails;
        }
    }

    public boolean softDelete(long id) {
        String query = "UPDATE order_details SET deleted_at = :deletedAt WHERE id = :id AND deleted_at IS NULL";
        try (Connection con = sql2o.open()) {
            int rowsUpdated = con.createQuery(query)
                    .addParameter("deletedAt", LocalDateTime.now())
                    .addParameter("id", id)
                    .executeUpdate()
                    .getResult();

            return rowsUpdated > 0;
        }
    }
}
