package com.emontazysta.service.impl;

import com.emontazysta.mapper.WarehousemanMapper;
import com.emontazysta.model.Warehouseman;
import com.emontazysta.model.dto.WarehousemanDto;
import com.emontazysta.repository.WarehousemanRepository;
import com.emontazysta.service.WarehousemanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehousemanServiceImpl implements WarehousemanService {

    private final WarehousemanRepository repository;
    private final WarehousemanMapper warehousemanMapper;

    @Override
    public List<WarehousemanDto> getAll() {
        return repository.findAll().stream()
                .map(warehousemanMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public WarehousemanDto getById(Long id) {
        Warehouseman warehouseman = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return warehousemanMapper.toDto(warehouseman);
    }

    @Override
    public WarehousemanDto add(WarehousemanDto warehousemanDto) {
        Warehouseman warehouseman = warehousemanMapper.toEntity(warehousemanDto);
        return warehousemanMapper.toDto(repository.save(warehouseman));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
