package com.example.backend.services;

import com.example.backend.entities.OrderDetails;
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

    public List<OrderDetails> getOrderDetails() {
        return repo.findAll();
    }

    public OrderDetails getOrderDetails(long id) {
        OrderDetails orderDetail = repo.findById(id);
        if (orderDetail == null) {
            throw new NoSuchElementException("Order Detail Not Found");
        }
        return orderDetail;
    }

    public OrderDetails postOrderDetails(OrderDetails orderDetails) {
        return repo.save(orderDetails);
    }

    public OrderDetails putOrderDetails(long id, OrderDetails orderDetails) {
        return repo.update(id, orderDetails);
    }

    public boolean softDeleteOrderDetails(long id) {
        OrderDetails orderDetails = getOrderDetails(id);
        if (orderDetails == null) {
            throw new NoSuchElementException("Order Detail Not Found");
        }
        return repo.softDelete(id);
    }


}
