package com.emontazysta.mapper;

import com.emontazysta.model.Orders;
import com.emontazysta.model.SalesRepresentative;
import com.emontazysta.model.dto.SalesRepresentativeDto;
import com.emontazysta.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SalesRepresentativeMapper {

    private final OrderRepository orderRepository;

    public SalesRepresentativeDto toDto(SalesRepresentative salesRepresentative) {

        return SalesRepresentativeDto.builder()
                .id(salesRepresentative.getId())
                .firstName(salesRepresentative.getFirstName())
                .lastName(salesRepresentative.getLastName())
                .username(salesRepresentative.getUsername())
                .email(salesRepresentative.getEmail())
                .phone(salesRepresentative.getPhone())
                .pesel(salesRepresentative.getPesel())
                .orders(salesRepresentative.getOrders().stream().map(Orders::getId).collect(Collectors.toList()))
                .build();
    }

    public SalesRepresentative toEntity(SalesRepresentativeDto salesRepresentativeDto) {

        List<Orders> ordersList = new ArrayList<>();
        salesRepresentativeDto.getOrders().forEach(orderId -> ordersList.add(orderRepository.getReferenceById(orderId)));

        SalesRepresentative salesRepresentative = new SalesRepresentative();
        salesRepresentative.setId(salesRepresentativeDto.getId());
        salesRepresentative.setFirstName(salesRepresentativeDto.getFirstName());
        salesRepresentative.setLastName(salesRepresentativeDto.getLastName());
        salesRepresentative.setUsername(salesRepresentativeDto.getUsername());
        salesRepresentative.setEmail(salesRepresentativeDto.getEmail());
        salesRepresentative.setPhone(salesRepresentativeDto.getPhone());
        salesRepresentative.setPesel(salesRepresentativeDto.getPesel());
        salesRepresentative.setOrders(ordersList);

        return salesRepresentative;
    }
}
