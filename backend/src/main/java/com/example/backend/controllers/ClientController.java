package com.example.backend.controllers;

import com.example.backend.entities.ClientEntity;
import com.example.backend.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<List<ClientEntity>> getClients() {
        return new ResponseEntity<>(clientService.getAllClients(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientEntity> getClientById(@PathVariable Long id) {
        return new ResponseEntity<>(clientService.getClientById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientEntity> putClient(@PathVariable Long id, @RequestBody ClientEntity client) {
        return new ResponseEntity<>(clientService.updateClient(id, client), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteClient(@PathVariable Long id) {
        HashMap<String, Boolean> response = new HashMap<>();
        response.put("success", clientService.deleteClient(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
