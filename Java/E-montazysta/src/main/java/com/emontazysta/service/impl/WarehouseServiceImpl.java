package com.emontazysta.service.impl;

import com.emontazysta.mapper.WarehouseMapper;
import com.emontazysta.model.Company;
import com.emontazysta.model.Warehouse;
import com.emontazysta.model.dto.ElementDto;
import com.emontazysta.model.dto.ElementInWarehouseDto;
import com.emontazysta.model.dto.WarehouseDto;
import com.emontazysta.model.dto.WarehouseLocationDto;
import com.emontazysta.model.searchcriteria.ElementSearchCriteria;
import com.emontazysta.model.searchcriteria.WarehouseSearchCriteria;
import com.emontazysta.repository.CompanyRepository;
import com.emontazysta.repository.WarehouseRepository;
import com.emontazysta.repository.criteria.WarehouseCriteriaRepository;
import com.emontazysta.service.ElementInWarehouseService;
import com.emontazysta.service.ElementService;
import com.emontazysta.service.WarehouseService;
import com.emontazysta.util.AuthUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository repository;
    private final CompanyRepository companyRepository;
    private final WarehouseMapper warehouseMapper;
    private final WarehouseCriteriaRepository warehouseCriteriaRepository;
    private final AuthUtils authUtils;
    private final ElementService elementService;
    private final ElementInWarehouseService elementInWarehouseService;

    @Override
    public List<WarehouseDto> getAll() {
        return repository.findAll().stream()
                .map(warehouseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public WarehouseDto getById(Long id) {
        Warehouse warehouse = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return warehouseMapper.toDto(warehouse);
    }

    @Override
    public WarehouseDto add(WarehouseDto warehouseDto) {
        Warehouse warehouse = warehouseMapper.toEntity(warehouseDto);
        Company company = companyRepository.findById(authUtils.getLoggedUserCompanyId()).orElseThrow(EntityNotFoundException::new);
        warehouse.setCompany(company);
        return warehouseMapper.toDto(repository.save(warehouse));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public WarehouseDto update(Long id, WarehouseDto warehouseDto) {

        Warehouse updatedWarehouse = warehouseMapper.toEntity(warehouseDto);
        Warehouse warehouse = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        warehouse.setName(updatedWarehouse.getName());
        warehouse.setDescription(updatedWarehouse.getDescription());
        warehouse.setOpeningHours(updatedWarehouse.getOpeningHours());
        warehouse.setCompany(updatedWarehouse.getCompany());
        warehouse.setLocation(updatedWarehouse.getLocation());
        warehouse.setElementInWarehouses(updatedWarehouse.getElementInWarehouses());
        warehouse.setTools(updatedWarehouse.getTools());

        return warehouseMapper.toDto(repository.save(warehouse));
    }

    public List<WarehouseLocationDto> findAllWithFilters(WarehouseSearchCriteria warehouseSearchCriteria){
        return warehouseCriteriaRepository.findAllWithFilters(warehouseSearchCriteria);
    }

    @Override
    public WarehouseDto addWithWarehouseCount(WarehouseDto warehouseDto) {
        Company company = companyRepository.findById(authUtils.getLoggedUserCompanyId()).orElseThrow(EntityNotFoundException::new);
        warehouseDto.setCompanyId(company.getId());
        Warehouse warehouse = repository.save(warehouseMapper.toEntity(warehouseDto));

        List<ElementDto> elementsToAdd = elementService.getFilteredElements(new ElementSearchCriteria());

        elementsToAdd.forEach(elementDto -> {
            ElementInWarehouseDto elementInWarehouseDto = ElementInWarehouseDto.builder()
                    .inWarehouseCount(0)
                    .inUnitCount(0)
                    .rack("")
                    .shelf("")
                    .elementId(elementDto.getId())
                    .warehouseId(warehouse.getId())
                    .build();

            elementInWarehouseService.add(elementInWarehouseDto);
        });

        return warehouseMapper.toDto(warehouse);
    }
}
