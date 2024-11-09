package com.example.backend.services;

import com.example.backend.entities.OrderDetailEntity;
import com.example.backend.exceptions.EntityNotFoundException;
import com.example.backend.repositories.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public List<OrderDetailEntity> getOrderDetails() {
        return orderDetailRepository.findAll();
    }

    public OrderDetailEntity getOrderDetailById(Long id) {
        OrderDetailEntity orderDetail = orderDetailRepository.findById(id);
        if (orderDetail == null) {
            throw new EntityNotFoundException("Order Detail Not Found");
        }
        return orderDetail;
    }

    public OrderDetailEntity addOrderDetail(OrderDetailEntity orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    public OrderDetailEntity updateOrderDetail(Long id, OrderDetailEntity orderDetail) {
        return orderDetailRepository.update(id, orderDetail);
    }

    public boolean deleteOrderDetail(Long id) {
        OrderDetailEntity orderDetail = orderDetailRepository.findById(id);
        if (orderDetail == null) {
            throw new EntityNotFoundException("Order Detail Not Found");
        }
        return orderDetailRepository.delete(id);
    }
}
