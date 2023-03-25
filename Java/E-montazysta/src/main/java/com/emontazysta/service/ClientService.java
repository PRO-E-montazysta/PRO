package com.emontazysta.service;

import com.emontazysta.model.dto.ClientDto;
import com.emontazysta.model.searchcriteria.ClientSearchCriteria;

import java.security.Principal;
import java.util.List;

public interface ClientService {

    List<ClientDto> getAll();
    ClientDto getById(Long id);
    ClientDto add(ClientDto client);
    void delete(Long id);
    ClientDto update(Long id, ClientDto client);
    public List<ClientDto> getFilteredOrders(ClientSearchCriteria clientSearchCriteria, Principal principal);
}
