package com.example.backend.repositories;

import com.example.backend.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class OrderRepository {

    @Autowired
    private final Sql2o sql2o;
    public OrderRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public List<Order> findAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM orders")
                    .executeAndFetch(Order.class);
        }
    }

    public Order findById(long id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM orders WHERE id=:id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Order.class);
        }
    }

    public Order save(Order order) {
        try (Connection con = sql2o.open()) {
            String query = "INSERT INTO orders (order_date, state, client_id, total) " +
            "VALUES (:order_date, :state, :client_id, :total) RETURNING id";
            int id = con.createQuery(query, true)
                    .addParameter("order_date", order.getOrder_date())
                    .addParameter("state", order.getState())
                    .addParameter("client_id", order.getClient_id())
                    .addParameter("total", order.getTotal())
                    .executeUpdate()
                    .getKey(Integer.class);
            order.setId(id);
            return order;
        }
    }

    public Order update(long id, Order order) {
        try (Connection con = sql2o.open()) {
            String query = "UPDATE orders " +
                    "SET state = :state " +
                    "WHERE id = :id";
            con.createQuery(query)
                    .addParameter("state", order.getState())
                    .addParameter("id", id)
                    .executeUpdate();
            Order updatedOrder = con.createQuery("SELECT * FROM orders WHERE id =:id")
                    .addParameter("id", order.getId())
                    .executeAndFetchFirst(Order.class);
            return updatedOrder;
        }
    }

    public boolean softDelete(long id) {
        String query = "UPDATE orders SET deleted_at = :deletedAt WHERE id = :id AND deleted_at IS NULL";
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
