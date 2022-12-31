package com.emontazysta.service;

import com.emontazysta.model.Client;

import java.util.List;

public interface ClientService {

    List<Client> getAll();
    Client getById(Long id);
    void add(Client client);
    void delete(Long id);
    void edit(Long id, Client client);
}
