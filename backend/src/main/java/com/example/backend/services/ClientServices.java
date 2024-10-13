package com.example.backend.services;

import com.example.backend.entities.Client;
import com.example.backend.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ClientServices {
    private final ClientRepository clientRepository;

    public ClientServices(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public Client getClient(long id) {
        Client client = clientRepository.findById(id);

        if (client == null) {
            throw new NoSuchElementException("Client not found");
        }

        return client;
    }

    public Client postClient(Client client) {
        Client existingClientByEmail = clientRepository.findByEmail(client.getEmail());
        if (existingClientByEmail != null) {
            throw new NoSuchElementException("The email is already used");
        }

        return clientRepository.save(client);
    }

    public Client putClient(long id, Client client) {
        Client existingClientByEmail = clientRepository.findByEmail(client.getEmail());
        if (existingClientByEmail != null) {
            throw new NoSuchElementException("The email is already used");
        }

        return clientRepository.update(id, client);
    }
}
