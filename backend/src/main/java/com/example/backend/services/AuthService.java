package com.example.backend.services;

import com.example.backend.dtos.LoginDTO;
import com.example.backend.dtos.RegisterDTO;
import com.example.backend.entities.ClientEntity;
import com.example.backend.jwt.JwtUtil;
import com.example.backend.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ClientEntity register(RegisterDTO clientDTO) {
        ClientEntity existingClientEntityByEmail = clientRepository.findByEmail(clientDTO.getEmail());
        if (existingClientEntityByEmail != null) {
            throw new IllegalStateException("The email is already used");
        }

        ClientEntity client = ClientEntity.builder()
                .name(clientDTO.getName())
                .address(clientDTO.getAddress())
                .email(clientDTO.getEmail())
                .password(passwordEncoder.encode(clientDTO.getPassword()))
                .phone(clientDTO.getPhone())
                .build();

        return clientRepository.save(client);
    }

    public String login(LoginDTO loginDTO) {
        ClientEntity clientEntity = clientRepository.findByEmail(loginDTO.getEmail());

        if (clientEntity == null) {
            throw new IllegalStateException("The email or password is incorrect");
        }

        if (!passwordEncoder.matches(loginDTO.getPassword(), clientEntity.getPassword())) {
            throw new IllegalStateException("The email or password is incorrect");
        }

        return JwtUtil.createToken(loginDTO.getEmail());

    }
}
