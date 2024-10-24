package com.example.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientEntity {
    private long id;
    private String name;
    private String address;
    private String email;
    private String password;
    private String phone;
}
