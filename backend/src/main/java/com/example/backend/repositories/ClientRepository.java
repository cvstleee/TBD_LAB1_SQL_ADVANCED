package com.example.backend.repositories;

import com.example.backend.entities.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class ClientRepository {
    @Autowired
    private final Sql2o sql2o;

    public ClientRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public List<Client> findAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM clients")
                    .executeAndFetch(Client.class);
        }
    }

    public Client findById(long id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM clients WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Client.class);
        }
    }

    public Client findByEmail(String email) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM clients WHERE email = :email")
                    .addParameter("email", email)
                    .executeAndFetchFirst((Client.class));
        }
    }

    public Client findByPhone(String phone) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM clients WHERE phone = :phone")
                    .addParameter("phone", phone)
                    .executeAndFetchFirst(Client.class);
        }
    }

    public Client save(Client client) {
        String query = "INSERT INTO clients (name, address, email, password, phone) " +
                "VALUES (:name, :address, :email, :password, :phone) RETURNING id";

        try (Connection con = sql2o.open()) {
            int id = con.createQuery(query, true)
                    .addParameter("name", client.getName())
                    .addParameter("address", client.getAddress())
                    .addParameter("email", client.getEmail())
                    .addParameter("password", client.getPassword())
                    .addParameter("phone", client.getPhone())
                    .executeUpdate()
                    .getKey(Integer.class);

            client.setId(id);
            return client;
        }
    }

    public Client update(long id, Client client) {
        String query = "UPDATE clients " +
                "SET email = :email " +
                "WHERE id = :id";

        try (Connection con = sql2o.open()) {
            con.createQuery(query)
                    .addParameter("email", client.getEmail())
                    .addParameter("id", id)
                    .executeUpdate();

            Client updatedClient = con.createQuery("SELECT * FROM clients WHERE id = :id")
                    .addParameter("id", client.getId())
                    .executeAndFetchFirst(Client.class);

            return updatedClient;
        }
    }
}
