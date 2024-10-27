package com.example.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {
    private long id;
    private Timestamp order_date;
    private String state;
    private long client_id;
    private double total;
    private Timestamp deleted_at;
}
