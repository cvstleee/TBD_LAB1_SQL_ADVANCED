package com.example.backend.repositories;

import com.example.backend.dtos.QueryDTO;
import com.example.backend.entities.OrderEntity;
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

    public List<OrderEntity> findAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM orders WHERE deleted_at IS NULL")
                    .executeAndFetch(OrderEntity.class);
        }
    }

    public OrderEntity findById(long id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM orders WHERE id=:id AND deleted_at IS NULL")
                    .addParameter("id", id)
                    .executeAndFetchFirst(OrderEntity.class);
        }
    }

    public OrderEntity save(OrderEntity order, int clientId) {
        try (Connection con = sql2o.beginTransaction()) {
            con.createQuery("SET LOCAL application.client_id = " + clientId).executeUpdate();

            String query = "INSERT INTO orders (order_date, state, client_id, total, shipping_date) " +
            "VALUES (:order_date, :state, :client_id, :total, :shipping_date) RETURNING id";
            int id = con.createQuery(query, true)
                    .addParameter("order_date", order.getOrder_date())
                    .addParameter("state", order.getState())
                    .addParameter("client_id", order.getClient_id())
                    .addParameter("total", order.getTotal())
                    .addParameter("shipping_date", order.getShipping_date())
                    .executeUpdate()
                    .getKey(Integer.class);
            con.commit();
            order.setId(id);
            return order;
        }
    }

    public OrderEntity update(long id, OrderEntity order, int clientId) {
        try (Connection con = sql2o.beginTransaction()) {
            con.createQuery("SET LOCAL application.client_id = " + clientId).executeUpdate();

            String query = "UPDATE orders SET state = :state, total = :total WHERE id = :id";
            con.createQuery(query)
                    .addParameter("state", order.getState())
                    .addParameter("total", order.getTotal())
                    .addParameter("id", id)
                    .executeUpdate();
            con.commit();
            OrderEntity updatedOrder = con.createQuery("SELECT * FROM orders WHERE id =:id")
                    .addParameter("id", order.getId())
                    .executeAndFetchFirst(OrderEntity.class);
            return updatedOrder;
        }
    }

    public boolean delete(long id, int clientId) {
        String query = "UPDATE orders SET deleted_at = :deletedAt WHERE id = :id AND deleted_at IS NULL";
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

    public List<QueryDTO> getAverageShippingTimes() {
        String query = "SELECT \n" +
                "    c.name AS category_name,\n" +
                "    AVG(EXTRACT(EPOCH FROM (o.shipping_date - o.order_date)) / 3600) AS average_shipping_time_hours\n" +
                "FROM \n" +
                "    orders o\n" +
                "JOIN \n" +
                "    order_details od ON od.order_id = o.id\n" +
                "JOIN \n" +
                "    products p ON p.id = od.product_id\n" +
                "JOIN \n" +
                "    categories c ON p.category_id = c.id\n" +
                "WHERE \n" +
                "    o.order_date >= DATE_TRUNC('quarter', CURRENT_DATE - INTERVAL '3 months') \n" +
                "    AND o.order_date < DATE_TRUNC('quarter', CURRENT_DATE)\n" +
                "    AND o.shipping_date IS NOT NULL\n" +
                "GROUP BY \n" +
                "    c.name\n" +
                "ORDER BY \n" +
                "    average_shipping_time_hours DESC;";
        try (Connection con = sql2o.open()) {
            return con.createQuery(query)
                    .executeAndFetch(QueryDTO.class);
        }
    }

}
