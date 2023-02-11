package com.emontazysta.mapper;

import com.emontazysta.model.Client;
import com.emontazysta.model.Orders;
import com.emontazysta.model.dto.ClientDto;
import com.emontazysta.service.CompanyService;
import com.emontazysta.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ClientMapper {

    private final CompanyService companyService;
    private final OrdersService orderService;

    public ClientDto toDto(Client client) {
        return ClientDto.builder()
                .id(client.getId())
                .name(client.getName())
                .contactDetails(client.getContactDetails())
                .companyId(client.getCompany().getId())
                .orders(client.getOrders().stream().map(Orders::getId).collect(Collectors.toList()))
                .build();
    }

    public Client toEntity(ClientDto clientDto) {

        List<Orders> ordersList = new ArrayList<>();
        clientDto.getOrders().forEach(orderId -> ordersList.add(orderService.getById(orderId)));

        return Client.builder()
                .id(clientDto.getId())
                .name(clientDto.getName())
                .contactDetails(clientDto.getContactDetails())
                .company(companyService.getById(clientDto.getCompanyId()))
                .orders(ordersList)
                .build();
    }
}
