package com.emontazysta.service.impl;

import com.emontazysta.model.Orders;
import com.emontazysta.repository.OrderRepository;
import com.emontazysta.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {

    private final OrderRepository repository;

    @Override
    public List<Orders> getAll() {
        return repository.findAll();
    }

    @Override
    public Orders getById(Long id) {
        return repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void add(Orders orders) {
        orders.setCreatedAt(new Date());
        orders.setEditedAt(null);

        repository.save(orders);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void update(Long id, Orders orders) {
        Orders updatedOrders = this.getById(id);
        updatedOrders.setName(orders.getName());
        updatedOrders.setTypeOfStatus(orders.getTypeOfStatus());
        updatedOrders.setTypeOfPriority(orders.getTypeOfPriority());
        updatedOrders.setPlannedStart(orders.getPlannedStart());
        updatedOrders.setPlannedEnd(orders.getPlannedEnd());
        updatedOrders.setCompany(orders.getCompany());

        repository.save(updatedOrders);
    }
}
