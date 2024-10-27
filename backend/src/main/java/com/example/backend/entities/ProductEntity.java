package com.example.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
    private long id;
    private String name;
    private String description;
    private double price;
    private int stock;
    private String state;
    private long category_id;
    private Timestamp deleted_at;
}
