package com.example.backend.controllers;

import com.example.backend.entities.OrderDetailEntity;
import com.example.backend.services.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/ordersDetails")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping
    public ResponseEntity<List<OrderDetailEntity>> getOrderDetails() {
        List<OrderDetailEntity> ordersDetails = orderDetailService.getOrderDetails();
        return new ResponseEntity<>(ordersDetails, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailEntity> getOrderDetailById(@PathVariable Long id) {
        return new ResponseEntity<>(orderDetailService.getOrderDetailById(id), HttpStatus.OK);
    }

    // orderDetails for Order
    @GetMapping("/order/{idOrder}")
    public ResponseEntity<List<OrderDetailEntity>> getOrderDetailByIdOrder(@PathVariable Long idOrder) {
        List<OrderDetailEntity> ordersDetails = orderDetailService.getOrderDetailsByIdOrder(idOrder);
        return new ResponseEntity<>(ordersDetails, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderDetailEntity> postOrderDetail(@RequestBody OrderDetailEntity orderDetails) {
        return new ResponseEntity<>(orderDetailService.addOrderDetail(orderDetails), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDetailEntity> putOrderDetail(@PathVariable Long id, @RequestBody OrderDetailEntity orderDetails) {
        return new ResponseEntity<>(orderDetailService.updateOrderDetail(id, orderDetails), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteOrderDetail(@PathVariable Long id) {
        HashMap<String, Boolean> response = new HashMap<>();
        response.put("success", orderDetailService.deleteOrderDetail(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
