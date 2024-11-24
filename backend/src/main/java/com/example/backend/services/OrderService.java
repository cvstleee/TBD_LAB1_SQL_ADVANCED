package com.example.backend.services;

import com.example.backend.dtos.QueryDTO;
import com.example.backend.entities.ClientEntity;
import com.example.backend.entities.OrderEntity;
import com.example.backend.exceptions.EntityNotFoundException;
import com.example.backend.repositories.ClientRepository;
import com.example.backend.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AuthService authService;

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
        ClientEntity possibleClient = clientRepository.findById(order.getClient_id());
        int authIdClient = authService.getAuthIdClient();
        if (possibleClient == null) {
            throw new EntityNotFoundException("Client not found");
        }

        return orderRepository.save(order, authIdClient);
    }

    public OrderEntity updateOrder(Long id, OrderEntity order) {
        OrderEntity possibleOrder = orderRepository.findById(id);
        int authIdClient = authService.getAuthIdClient();
        if (possibleOrder == null) {
            throw new EntityNotFoundException("Order not found");
        }

        return orderRepository.update(id, order, authIdClient);
    }

    public boolean deletedOrder(Long id) {
        OrderEntity order = orderRepository.findById(id);
        int authIdClient = authService.getAuthIdClient();
        if (order == null) {
            throw new EntityNotFoundException("Order not found");
        }

        return orderRepository.delete(id, authIdClient);
    }

    public List<QueryDTO> getAverageShippingTimes() {
        return orderRepository.getAverageShippingTimes();
    }
}
