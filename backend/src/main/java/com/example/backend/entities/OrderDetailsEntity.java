package com.example.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsEntity {
    private long id;
    private long order_id;
    private long product_id;
    private int quantity;
    private double unit_price;
    private Timestamp deleted_at;

}
