package com.emontazysta.service.impl;

import com.emontazysta.mapper.WarehouseManagerMapper;
import com.emontazysta.model.WarehouseManager;
import com.emontazysta.model.dto.WarehouseManagerDto;
import com.emontazysta.repository.WarehouseManagerRepository;
import com.emontazysta.service.WarehouseManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehouseManagerServiceImpl implements WarehouseManagerService {

    private final WarehouseManagerRepository repository;
    private final WarehouseManagerMapper warehouseManagerMapper;

    @Override
    public List<WarehouseManagerDto> getAll() {
        return repository.findAll().stream()
                .map(warehouseManagerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public WarehouseManagerDto getById(Long id) {
        WarehouseManager warehouseManager = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return warehouseManagerMapper.toDto(warehouseManager);
    }

    @Override
    public WarehouseManagerDto add(WarehouseManagerDto warehouseManagerDto) {
        WarehouseManager warehouseManager = warehouseManagerMapper.toEntity(warehouseManagerDto);
        return warehouseManagerMapper.toDto(repository.save(warehouseManager));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
