package com.example.backend.services;

import com.example.backend.entities.OrderDetailEntity;
import com.example.backend.entities.OrderEntity;
import com.example.backend.entities.ProductEntity;
import com.example.backend.exceptions.EntityNotFoundException;
import com.example.backend.repositories.OrderDetailRepository;
import com.example.backend.repositories.OrderRepository;
import com.example.backend.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

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
        OrderEntity possibleOrder = orderRepository.findById(orderDetail.getOrder_id());
        if (possibleOrder == null) {
            throw new EntityNotFoundException("Order Not Found");
        }
        ProductEntity possibleProduct = productRepository.findById(orderDetail.getProduct_id());
        if (possibleProduct == null) {
            throw new EntityNotFoundException("Product Not Found");
        }

        return orderDetailRepository.save(orderDetail);
    }

    public OrderDetailEntity updateOrderDetail(Long id, OrderDetailEntity orderDetail) {
        OrderDetailEntity possibleOrderDetail = orderDetailRepository.findById(id);
        if (possibleOrderDetail == null) {
            throw new EntityNotFoundException("Order Detail Not Found");
        }

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
