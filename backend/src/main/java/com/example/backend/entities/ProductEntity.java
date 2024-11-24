package com.example.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private int stock;
    private String state;
    private int category_id;
    private Timestamp deleted_at;
}
