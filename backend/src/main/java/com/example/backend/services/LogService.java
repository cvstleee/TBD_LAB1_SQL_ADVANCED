package com.example.backend.services;

import com.example.backend.dtos.LogDTO;
import com.example.backend.repositories.LogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {

    private final LogRepository logRepository;

    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public List<LogDTO> getAllLogs() {
        return logRepository.findAllLogs();
    }
}
