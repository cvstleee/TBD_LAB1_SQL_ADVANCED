package com.example.backend.services;

import com.example.backend.entities.ClientEntity;
import com.example.backend.exceptions.EntityNotFoundException;
import com.example.backend.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public List<ClientEntity> getAllClients() {
        return clientRepository.findAll();
    }

    public ClientEntity getClientById(Long id) {
        ClientEntity clientEntity = clientRepository.findById(id);

        if (clientEntity == null) {
            throw new EntityNotFoundException("Client not found");
        }

        return clientEntity;
    }

    public ClientEntity updateClient(Long id, ClientEntity clientEntity) {
        ClientEntity existingClientEntityByEmail = clientRepository.findByEmail(clientEntity.getEmail());

        if (existingClientEntityByEmail != null) {
            throw new IllegalStateException("The email is already used");
        }

        return clientRepository.update(id, clientEntity);
    }

    public boolean deleteClient(Long id) {
        ClientEntity clientEntity = clientRepository.findById(id);

        if (clientEntity == null) {
            throw new EntityNotFoundException("Client not found");
        }

        return clientRepository.softDelete(id);
    }
}
