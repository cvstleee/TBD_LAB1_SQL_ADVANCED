package com.example.backend.services;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.backend.dtos.LoginDTO;
import com.example.backend.dtos.LoginResponseDTO;
import com.example.backend.dtos.RegisterDTO;
import com.example.backend.entities.ClientEntity;
import com.example.backend.exceptions.EntityNotFoundException;
import com.example.backend.jwt.JwtUtil;
import com.example.backend.repositories.ClientRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private ClientRepository clientRepository;

    public ClientEntity register(RegisterDTO clientDTO) {
        ClientEntity existingClientEntityByEmail = clientRepository.findByEmail(clientDTO.getEmail());
        if (existingClientEntityByEmail != null) {
            throw new IllegalStateException("The email is already used");
        }

        ClientEntity client = ClientEntity.builder()
                .name(clientDTO.getName())
                .address(clientDTO.getAddress())
                .email(clientDTO.getEmail())
                .password(clientDTO.getPassword())
                .phone(clientDTO.getPhone())
                .build();

        return clientRepository.save(client);
    }

    public LoginResponseDTO login(LoginDTO loginDTO) {
        ClientEntity clientEntity = clientRepository.findByEmail(loginDTO.getEmail());
        if (clientEntity == null) {
            throw new IllegalStateException("The email or password is incorrect");
        }
        if (!clientEntity.getPassword().equals(loginDTO.getPassword())) {
            throw new IllegalStateException("The email or password is incorrect");
        }

        return LoginResponseDTO.builder()
                .token(JwtUtil.createToken(loginDTO.getEmail()))
                .userId(clientEntity.getId())
                .build();
    }

    public void verifyToken(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JWT")) {
                    try {
                        String token = cookie.getValue();
                        DecodedJWT decodedJWT = JwtUtil.verifyToken(token);
                        return;
                    } catch (JWTVerificationException e) {
                        throw new IllegalStateException("Invalid JWT");
                    }
                }
            }
            throw new IllegalStateException("JWT not found");
        }
        throw new IllegalStateException("Cookies not found");
    }

    public ClientEntity getClientByToken(Cookie[] cookies) {
        String token = JwtUtil.getJwtFromCookies(cookies);
        String email = JwtUtil.extractEmailFromJwt(token);
        ClientEntity clientEntity = clientRepository.findByEmail(email);

        if (clientEntity == null) {
            throw new EntityNotFoundException("Client not found");
        }

        return clientEntity;
    }

    public int getAuthIdClient() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            ClientEntity clientEntity = clientRepository.findByEmail(email);

            if (clientEntity == null) {
                throw new EntityNotFoundException("Client not found");
            }
            return clientEntity.getId();
        }
        throw new EntityNotFoundException("No authenticated client found");
    }
}
