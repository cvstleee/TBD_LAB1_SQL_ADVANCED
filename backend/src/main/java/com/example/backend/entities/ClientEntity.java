package com.example.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientEntity {
    private int id;
    private String name;
    private String address;
    private String email;
    private String password;
    private String phone;
    private Timestamp deleted_at;
}
