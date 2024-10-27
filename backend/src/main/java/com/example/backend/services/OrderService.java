package com.example.backend.services;

import com.example.backend.entities.Order;
import com.example.backend.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderService {

    @Autowired
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(long id) {
        Order order = orderRepository.findById(id);
        if (order == null) {
            throw new NoSuchElementException("Order not found");
        }
        return order;
    }

    public Order postOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order updateOrder(long id, Order order) {
        return orderRepository.update(id, order);
    }

    public boolean softDeletedOrder(long id) {
        Order order = getOrderById(id);
        if (order == null) {
            throw new NoSuchElementException("Order not found");
        }
        return orderRepository.softDelete(id);
    }
}
