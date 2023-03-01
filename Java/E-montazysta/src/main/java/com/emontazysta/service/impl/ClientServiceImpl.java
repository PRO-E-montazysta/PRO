package com.emontazysta.service.impl;

import com.emontazysta.mapper.ClientMapper;
import com.emontazysta.model.Client;
import com.emontazysta.model.dto.ClientDto;
import com.emontazysta.repository.ClientRepository;
import com.emontazysta.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;
    private final ClientMapper clientMapper;

    @Override
    public List<ClientDto> getAll() {
        return repository.findAll().stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClientDto getById(Long id) {
        Client client = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return clientMapper.toDto(client);
    }

    @Override
    public ClientDto add(ClientDto clientDto) {
        Client client = clientMapper.toEntity(clientDto);
        return clientMapper.toDto(repository.save(client));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public ClientDto update(Long id, ClientDto clientDto) {

        Client updatedClient = clientMapper.toEntity(clientDto);
        Client clientToUpdate = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        clientToUpdate.setName(updatedClient.getName());
        clientToUpdate.setContactDetails(updatedClient.getContactDetails());
        clientToUpdate.setCompany(updatedClient.getCompany());
        clientToUpdate.setOrders(updatedClient.getOrders());
        return clientMapper.toDto(repository.save(clientToUpdate));
    }
}
