package com.example.backend.controllers;

import com.example.backend.dtos.LogDTO;
import com.example.backend.services.LogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/log")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("/getAll")
    public List<LogDTO> getLogs() {
        return logService.getAllLogs();
    }
}
