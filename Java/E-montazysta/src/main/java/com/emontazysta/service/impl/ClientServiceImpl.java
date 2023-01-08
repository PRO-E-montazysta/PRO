package com.emontazysta.service.impl;

import com.emontazysta.model.Client;
import com.emontazysta.repository.ClientRepository;
import com.emontazysta.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;

    @Override
    public List<Client> getAll() {
        return repository.findAll();
    }

    @Override
    public Client getById(Long id) {
        return repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void add(Client client) {
        repository.save(client);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void update(Long id, Client client) {
        Client updatedClient = this.getById(id);
        updatedClient.setName(client.getName());
        updatedClient.setContactDetails(client.getContactDetails());

        repository.save(updatedClient);
    }
}
