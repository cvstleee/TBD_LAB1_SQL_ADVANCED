package com.example.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailEntity {
    private int id;
    private int order_id;
    private int product_id;
    private int quantity;
    private double unit_price;
    private Timestamp deleted_at;

}
