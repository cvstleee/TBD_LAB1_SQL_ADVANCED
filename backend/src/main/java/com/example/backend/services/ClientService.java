package com.example.backend.services;

import com.example.backend.dtos.ClientRegisterDTO;
import com.example.backend.entities.ClientEntity;
import com.example.backend.exceptions.EntityNotFoundException;
import com.example.backend.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<ClientEntity> getClients() {
        return clientRepository.findAll();
    }

    public ClientEntity getClient(long id) {
        ClientEntity clientEntity = clientRepository.findById(id);

        if (clientEntity == null) {
            throw new EntityNotFoundException("Client not found");
        }

        return clientEntity;
    }

    public ClientEntity register(ClientRegisterDTO clientDTO) {
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

    public ClientEntity putClient(long id, ClientEntity clientEntity) {
        ClientEntity existingClientEntityByEmail = clientRepository.findByEmail(clientEntity.getEmail());
        if (existingClientEntityByEmail != null) {
            throw new IllegalStateException("The email is already used");
        }

        return clientRepository.update(id, clientEntity);
    }
}
