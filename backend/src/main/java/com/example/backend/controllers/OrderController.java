package com.example.backend.controllers;

import com.example.backend.dtos.QueryDTO;
import com.example.backend.entities.OrderEntity;
import com.example.backend.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderEntity>> getOrders() {
        List<OrderEntity> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderEntity> getOrder(@PathVariable Long id) {
        return new ResponseEntity<>(orderService.getOrderById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderEntity> postOrder(@RequestBody OrderEntity order) {
        return new ResponseEntity<>(orderService.createOrder(order), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderEntity> putOrder(@PathVariable Long id, @RequestBody OrderEntity order) {
        return new ResponseEntity<>(orderService.updateOrder(id, order), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteOrder(@PathVariable Long id) {
        HashMap<String, Boolean> response = new HashMap<>();
        response.put("success", orderService.deletedOrder(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/query")
    public ResponseEntity<List<QueryDTO>> getAverageShippingTimesOrders () {
        return new ResponseEntity<>(orderService.getAverageShippingTimes(), HttpStatus.OK);
    }
}
