package com.example.backend.services;

import com.example.backend.entities.OrderEntity;
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

    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    public OrderEntity getOrderById(long id) {
        OrderEntity order = orderRepository.findById(id);
        if (order == null) {
            throw new NoSuchElementException("Order not found");
        }
        return order;
    }

    public OrderEntity postOrder(OrderEntity order) {
        return orderRepository.save(order);
    }

    public OrderEntity updateOrder(long id, OrderEntity order) {
        return orderRepository.update(id, order);
    }

    public boolean softDeletedOrder(long id) {
        OrderEntity order = getOrderById(id);
        if (order == null) {
            throw new NoSuchElementException("Order not found");
        }
        return orderRepository.softDelete(id);
    }
}
