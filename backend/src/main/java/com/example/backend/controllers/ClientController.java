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
    public ResponseEntity<List<ClientEntity>> getUsers() {
        return new ResponseEntity<>(clientService.getClients(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientEntity> getUser(@PathVariable long id) {
        return new ResponseEntity<>(clientService.getClient(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientEntity> putUser(@PathVariable long id, @RequestBody ClientEntity clientEntity) {
        return new ResponseEntity<>(clientService.putClient(id, clientEntity), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable long id) {
        boolean status = clientService.deleteClient(id);
        HashMap<String, Boolean> response = new HashMap<>();
        response.put("status", status);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
