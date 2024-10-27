package com.example.backend.controllers;

import com.example.backend.entities.OrderDetailsEntity;
import com.example.backend.services.OrderDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/ordersDetails")
public class OrderDetailsController {

    private final OrderDetailsService orderDetailsService;
    public OrderDetailsController(OrderDetailsService orderDetailsService) {
        this.orderDetailsService = orderDetailsService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDetailsEntity>> getAllOrderDetails() {
        List<OrderDetailsEntity> ordersDetails = orderDetailsService.getOrderDetails();
        return new ResponseEntity<>(ordersDetails, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetails(@PathVariable long id) {
        try {
            return new ResponseEntity<>(orderDetailsService.getOrderDetails(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> postOrderDetails(@RequestBody OrderDetailsEntity orderDetails) {
        try {
            return new ResponseEntity<>(orderDetailsService.postOrderDetails(orderDetails), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putOrderDetails(@PathVariable long id, @RequestBody OrderDetailsEntity orderDetails) {
        try {
            return new ResponseEntity<>(orderDetailsService.putOrderDetails(id, orderDetails), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetails(@PathVariable long id) {
        boolean isDeleted = orderDetailsService.softDeleteOrderDetails(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
