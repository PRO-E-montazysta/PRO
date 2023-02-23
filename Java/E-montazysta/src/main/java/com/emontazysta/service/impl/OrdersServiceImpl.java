package com.emontazysta.service.impl;

import com.emontazysta.mapper.OrdersMapper;
import com.emontazysta.model.Orders;
import com.emontazysta.model.dto.OrdersDto;
import com.emontazysta.repository.OrderRepository;
import com.emontazysta.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {

    private final OrderRepository repository;
    private final OrdersMapper ordersMapper;

    @Override
    public List<OrdersDto> getAll() {
        return repository.findAll().stream()
                .map(ordersMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrdersDto getById(Long id) {
        Orders order = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return ordersMapper.toDto(order);
    }

    @Override
    public OrdersDto add(OrdersDto ordersDto) {

        Orders order = ordersMapper.toEntity(ordersDto);
        order.setCreatedAt(new Date());
        order.setEditedAt(null);
        return ordersMapper.toDto(repository.save(order));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public OrdersDto update(Long id, OrdersDto ordersDto) {

        Orders updatedOrder = ordersMapper.toEntity(ordersDto);
        Orders order = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        order.setName(updatedOrder.getName());
        order.setTypeOfStatus(updatedOrder.getTypeOfStatus());
        order.setTypeOfPriority(updatedOrder.getTypeOfPriority());
        order.setPlannedStart(updatedOrder.getPlannedStart());
        order.setPlannedEnd(updatedOrder.getPlannedEnd());
        order.setEditedAt(new Date());
        order.setCompany(updatedOrder.getCompany());
        order.setManagedBy(updatedOrder.getManagedBy());
        order.setAssignedTo(updatedOrder.getAssignedTo());
        order.setSpecialist(updatedOrder.getSpecialist());
        order.setSalesRepresentative(updatedOrder.getSalesRepresentative());
        order.setLocation(updatedOrder.getLocation());
        order.setClient(updatedOrder.getClient());
        order.setOrderStages(updatedOrder.getOrderStages());
        order.setAttachments(updatedOrder.getAttachments());

        return ordersMapper.toDto(repository.save(order));
    }
}
