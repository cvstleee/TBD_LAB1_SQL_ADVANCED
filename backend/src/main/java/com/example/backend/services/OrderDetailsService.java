package com.example.backend.services;

import com.example.backend.entities.OrderDetailsEntity;
import com.example.backend.repositories.OrderDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderDetailsService {

    @Autowired
    private final OrderDetailsRepository repo;

    public OrderDetailsService(OrderDetailsRepository repo) {
        this.repo = repo;
    }

    public List<OrderDetailsEntity> getOrderDetails() {
        return repo.findAll();
    }

    public OrderDetailsEntity getOrderDetails(long id) {
        OrderDetailsEntity orderDetail = repo.findById(id);
        if (orderDetail == null) {
            throw new NoSuchElementException("Order Detail Not Found");
        }
        return orderDetail;
    }

    public OrderDetailsEntity postOrderDetails(OrderDetailsEntity orderDetails) {
        return repo.save(orderDetails);
    }

    public OrderDetailsEntity putOrderDetails(long id, OrderDetailsEntity orderDetails) {
        return repo.update(id, orderDetails);
    }

    public boolean softDeleteOrderDetails(long id) {
        OrderDetailsEntity orderDetails = getOrderDetails(id);
        if (orderDetails == null) {
            throw new NoSuchElementException("Order Detail Not Found");
        }
        return repo.softDelete(id);
    }


}
