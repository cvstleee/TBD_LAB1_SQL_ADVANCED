package com.example.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity {
    private int id;
    private String name;
    private Timestamp deleted_at;
}
