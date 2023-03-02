package com.emontazysta.service.impl;

import com.emontazysta.mapper.SalesRepresentativeMapper;
import com.emontazysta.model.SalesRepresentative;
import com.emontazysta.model.dto.SalesRepresentativeDto;
import com.emontazysta.repository.SalesRepresentativeRepository;
import com.emontazysta.service.SalesRepresentativeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesRepresentativeServiceImpl implements SalesRepresentativeService {

    private final SalesRepresentativeRepository repository;
    private final SalesRepresentativeMapper salesRepresentativeMapper;

    @Override
    public List<SalesRepresentativeDto> getAll() {
        return repository.findAll().stream()
                .map(salesRepresentativeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SalesRepresentativeDto getById(Long id) {
        SalesRepresentative salesRepresentative = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return salesRepresentativeMapper.toDto(salesRepresentative);
    }

    @Override
    public SalesRepresentativeDto add(SalesRepresentativeDto salesRepresentativeDto) {
        SalesRepresentative salesRepresentative = salesRepresentativeMapper.toEntity(salesRepresentativeDto);
        return salesRepresentativeMapper.toDto(repository.save(salesRepresentative));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public SalesRepresentativeDto update(Long id, SalesRepresentativeDto salesRepresentativeDto) {
        SalesRepresentative updatedSalesRepresentative = salesRepresentativeMapper.toEntity(salesRepresentativeDto);
        SalesRepresentative salesRepresentative = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        salesRepresentative.setFirstName(updatedSalesRepresentative.getFirstName());
        salesRepresentative.setLastName(updatedSalesRepresentative.getLastName());
        salesRepresentative.setEmail(updatedSalesRepresentative.getEmail());
        salesRepresentative.setUsername(updatedSalesRepresentative.getUsername());
        salesRepresentative.setPhone(updatedSalesRepresentative.getPhone());
        salesRepresentative.setPesel(updatedSalesRepresentative.getPesel());
        salesRepresentative.setUnavailabilities(updatedSalesRepresentative.getUnavailabilities());
        salesRepresentative.setNotifications(updatedSalesRepresentative.getNotifications());
        salesRepresentative.setEmployeeComments(updatedSalesRepresentative.getEmployeeComments());
        salesRepresentative.setElementEvents(updatedSalesRepresentative.getElementEvents());
        salesRepresentative.setEmployments(updatedSalesRepresentative.getEmployments());
        salesRepresentative.setAttachments(updatedSalesRepresentative.getAttachments());
        salesRepresentative.setToolEvents(updatedSalesRepresentative.getToolEvents());
        salesRepresentative.setOrders(updatedSalesRepresentative.getOrders());
        return salesRepresentativeMapper.toDto(repository.save(salesRepresentative));
    }
}
