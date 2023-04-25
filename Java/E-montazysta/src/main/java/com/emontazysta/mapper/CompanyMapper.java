package com.emontazysta.mapper;

import com.emontazysta.model.Client;
import com.emontazysta.model.Company;
import com.emontazysta.model.Employment;
import com.emontazysta.model.Orders;
import com.emontazysta.model.Warehouse;
import com.emontazysta.model.ToolType;
import com.emontazysta.model.dto.CompanyDto;
import com.emontazysta.repository.ClientRepository;
import com.emontazysta.repository.EmploymentRepository;
import com.emontazysta.repository.OrderRepository;
import com.emontazysta.repository.ToolTypeRepository;
import com.emontazysta.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompanyMapper {

    private final WarehouseRepository warehouseRepository;
    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final EmploymentRepository employmentRepository;
    private final ToolTypeRepository toolTypeRepository;

    public CompanyDto toDto(Company company) {
        return CompanyDto.builder()
                .id(company.getId())
                .companyName(company.getCompanyName())
                .createdAt(company.getCreatedAt())
                .status(company.getStatus())
                .statusReason(company.getStatusReason())
                .warehouses(company.getWarehouses().stream()
                        .filter(warehouse -> !warehouse.isDeleted())
                        .map(Warehouse::getId)
                        .collect(Collectors.toList()))
                .orders(company.getOrders().stream()
                        .filter(order -> !order.isDeleted())
                        .map(Orders::getId)
                        .collect(Collectors.toList()))
                .clients(company.getClients().stream()
                        .filter(client -> !client.isDeleted())
                        .map(Client::getId)
                        .collect(Collectors.toList()))
                .employments(company.getEmployments().stream()
                        .filter(employment -> !employment.isDeleted())
                        .map(Employment::getId)
                        .collect(Collectors.toList()))
                .toolTypes(company.getToolTypes().stream()
                        .filter(toolType -> !toolType.isDeleted())
                        .map(ToolType::getId)
                        .collect(Collectors.toList()))
                .build();
    }

    public Company toEntity(CompanyDto companyDto) {

        List<Warehouse> warehouseList = new ArrayList<>();
        companyDto.getWarehouses().forEach(warehouseId -> warehouseList.add(warehouseRepository.findById(warehouseId).orElseThrow(EntityNotFoundException::new)));

        List<Orders> ordersList = new ArrayList<>();
        companyDto.getOrders().forEach(orderId -> ordersList.add(orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new)));

        List<Client> clientList = new ArrayList<>();
        companyDto.getClients().forEach(clientId -> clientList.add(clientRepository.findById(clientId).orElseThrow(EntityNotFoundException::new)));

        List<Employment> employmentList = new ArrayList<>();
        companyDto.getEmployments().forEach(employmentId -> employmentList.add(employmentRepository.findById(employmentId).orElseThrow(EntityNotFoundException::new)));

        List<ToolType> toolTypeList = new ArrayList<>();
        companyDto.getToolTypes().forEach(toolTypeId -> toolTypeList.add(toolTypeRepository.findById(toolTypeId).orElseThrow(EntityNotFoundException::new)));

        return Company.builder()
                .id(companyDto.getId())
                .companyName(companyDto.getCompanyName())
                .createdAt(companyDto.getCreatedAt())
                .status(companyDto.getStatus())
                .statusReason(companyDto.getStatusReason())
                .warehouses(warehouseList)
                .orders(ordersList)
                .clients(clientList)
                .employments(employmentList)
                .toolTypes(toolTypeList)
                .build();
    }
}
