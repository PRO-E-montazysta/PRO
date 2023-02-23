package com.emontazysta.service;

import com.emontazysta.model.dto.ClientDto;

import java.util.List;

public interface ClientService {

    List<ClientDto> getAll();
    ClientDto getById(Long id);
    ClientDto add(ClientDto client);
    void delete(Long id);
    ClientDto update(Long id, ClientDto client);
}
