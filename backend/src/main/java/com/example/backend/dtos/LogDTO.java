package com.example.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogDTO {
    private Long id;
    private Integer userId;
    private String tableName;
    private Long elementId;
    private String operation;
    private String description;
    private Date date;
}
