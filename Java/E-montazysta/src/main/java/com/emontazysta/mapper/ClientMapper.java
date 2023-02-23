package com.emontazysta.mapper;

import com.emontazysta.model.Client;
import com.emontazysta.model.Orders;
import com.emontazysta.model.dto.ClientDto;
import com.emontazysta.repository.CompanyRepository;
import com.emontazysta.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ClientMapper {

    private final CompanyRepository companyRepository;
    private final OrderRepository orderRepository;

    public ClientDto toDto(Client client) {
        return ClientDto.builder()
                .id(client.getId())
                .name(client.getName())
                .contactDetails(client.getContactDetails())
                .companyId(client.getCompany() == null ? null : client.getCompany().getId())
                .orders(client.getOrders().stream().map(Orders::getId).collect(Collectors.toList()))
                .build();
    }

    public Client toEntity(ClientDto clientDto) {

        List<Orders> ordersList = new ArrayList<>();
        clientDto.getOrders().forEach(orderId -> ordersList.add(orderRepository.getReferenceById(orderId)));

        return Client.builder()
                .id(clientDto.getId())
                .name(clientDto.getName())
                .contactDetails(clientDto.getContactDetails())
                .company(clientDto.getCompanyId() == null ? null : companyRepository.getReferenceById(clientDto.getCompanyId()))
                .orders(ordersList)
                .build();
    }
}
