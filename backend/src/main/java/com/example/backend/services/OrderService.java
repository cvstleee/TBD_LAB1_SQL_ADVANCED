package com.example.backend.services;

import com.example.backend.entities.OrderEntity;
import com.example.backend.exceptions.EntityNotFoundException;
import com.example.backend.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    public OrderEntity getOrderById(Long id) {
        OrderEntity order = orderRepository.findById(id);
        if (order == null) {
            throw new EntityNotFoundException("Order not found");
        }
        return order;
    }

    public OrderEntity createOrder(OrderEntity order) {
        return orderRepository.save(order);
    }

    public OrderEntity updateOrder(Long id, OrderEntity order) {
        return orderRepository.update(id, order);
    }

    public boolean deletedOrder(Long id) {
        OrderEntity order = orderRepository.findById(id);
        if (order == null) {
            throw new EntityNotFoundException("Order not found");
        }
        return orderRepository.softDelete(id);
    }
}
