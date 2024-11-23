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
    @Autowired
    private AuthService authService;

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

    public List<OrderDetailEntity> getOrderDetailsByIdOrder(Long idOrder) {
        OrderEntity orderEntity = orderRepository.findById(idOrder);
        if (orderEntity == null) {
            throw new EntityNotFoundException("Order Not Found");
        }

        return orderDetailRepository.findByIdOrder(idOrder);
    }



    public OrderDetailEntity addOrderDetail(OrderDetailEntity orderDetail) {
        OrderEntity possibleOrder = orderRepository.findById(orderDetail.getOrder_id());
        int authIdClient = authService.getAuthIdClient();
        if (possibleOrder == null) {
            throw new EntityNotFoundException("Order Not Found");
        }
        ProductEntity possibleProduct = productRepository.findById(orderDetail.getProduct_id());
        if (possibleProduct == null) {
            throw new EntityNotFoundException("Product Not Found");
        }

        return orderDetailRepository.save(orderDetail, authIdClient);
    }

    public OrderDetailEntity updateOrderDetail(Long id, OrderDetailEntity orderDetail) {
        OrderDetailEntity possibleOrderDetail = orderDetailRepository.findById(id);
        int authIdClient = authService.getAuthIdClient();
        if (possibleOrderDetail == null) {
            throw new EntityNotFoundException("Order Detail Not Found");
        }

        return orderDetailRepository.update(id, orderDetail, authIdClient);
    }

    public boolean deleteOrderDetail(Long id) {
        OrderDetailEntity orderDetail = orderDetailRepository.findById(id);
        int authIdClient = authService.getAuthIdClient();
        if (orderDetail == null) {
            throw new EntityNotFoundException("Order Detail Not Found");
        }

        return orderDetailRepository.delete(id, authIdClient);
    }
}
