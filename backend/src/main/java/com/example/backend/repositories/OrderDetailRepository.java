package com.example.backend.repositories;

import com.example.backend.entities.OrderDetailEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class OrderDetailRepository {

    @Autowired
    private Sql2o sql2o;

    public List<OrderDetailEntity> findAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM order_details WHERE deleted_at IS NULL")
                    .executeAndFetch(OrderDetailEntity.class);
        }
    }

    public OrderDetailEntity findById(long id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM order_details WHERE id =:id AND deleted_at IS NULL")
                    .addParameter("id", id)
                    .executeAndFetchFirst(OrderDetailEntity.class);
        }
    }

    public List<OrderDetailEntity> findByIdOrder(long order_id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM order_details WHERE order_id =:order_id AND deleted_at IS NULL")
                    .addParameter("order_id", order_id)
                    .executeAndFetch(OrderDetailEntity.class);
        }
    }


    public OrderDetailEntity save(OrderDetailEntity orderDetails, int clientId) {
        try (Connection con = sql2o.beginTransaction()) {
            con.createQuery("SET LOCAL application.client_id = " + clientId).executeUpdate();

            String query = "INSERT INTO order_details (order_id, product_id, quantity, unit_price) " +
                    "VALUES (:order_id, :product_id, :quantity, :unit_price) RETURNING id";
            int id = con.createQuery(query, true)
                    .addParameter("order_id", orderDetails.getOrder_id())
                    .addParameter("product_id", orderDetails.getProduct_id())
                    .addParameter("quantity", orderDetails.getQuantity())
                    .addParameter("unit_price", orderDetails.getUnit_price())
                    .executeUpdate()
                    .getKey(Integer.class);
            con.commit();
            orderDetails.setId(id);
            return orderDetails;
        }
    }

    public OrderDetailEntity update(long id, OrderDetailEntity orderDetails, int clientId) {
        try (Connection con = sql2o.beginTransaction()) {
            con.createQuery("SET LOCAL application.client_id = " + clientId).executeUpdate();

            String query = "UPDATE order_details SET quantity =:quantity WHERE id =:id";
            con.createQuery(query)
                    .addParameter("quantity", orderDetails.getQuantity())
                    .addParameter("id", id)
                    .executeUpdate();
            con.commit();

            OrderDetailEntity updatedOrderDetails = con.createQuery("SELECT * FROM order_details WHERE id =:id")
                    .addParameter("id", orderDetails.getId())
                    .executeAndFetchFirst(OrderDetailEntity.class);
            return updatedOrderDetails;
        }
    }

    public boolean delete(long id, int clientId) {
        String query = "UPDATE order_details SET deleted_at = :deletedAt WHERE id = :id AND deleted_at IS NULL";
        try (Connection con = sql2o.beginTransaction()) {
            con.createQuery("SET LOCAL application.client_id = " + clientId).executeUpdate();

            int rowsUpdated = con.createQuery(query)
                    .addParameter("deletedAt", LocalDateTime.now())
                    .addParameter("id", id)
                    .executeUpdate()
                    .getResult();
            con.commit();

            return rowsUpdated > 0;
        }
    }
}
