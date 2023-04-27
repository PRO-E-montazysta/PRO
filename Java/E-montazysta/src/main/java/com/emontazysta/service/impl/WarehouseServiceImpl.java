package com.emontazysta.service.impl;

import com.emontazysta.mapper.WarehouseMapper;
import com.emontazysta.model.Company;
import com.emontazysta.model.Location;
import com.emontazysta.model.Warehouse;
import com.emontazysta.model.dto.*;
import com.emontazysta.model.searchcriteria.ElementSearchCriteria;
import com.emontazysta.model.searchcriteria.WarehouseSearchCriteria;
import com.emontazysta.repository.CompanyRepository;
import com.emontazysta.repository.LocationRepository;
import com.emontazysta.repository.WarehouseRepository;
import com.emontazysta.repository.criteria.ElementCriteriaRepository;
import com.emontazysta.repository.criteria.WarehouseCriteriaRepository;
import com.emontazysta.service.ElementInWarehouseService;
import com.emontazysta.service.ElementService;
import com.emontazysta.service.WarehouseService;
import com.emontazysta.util.AuthUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository repository;
    private final CompanyRepository companyRepository;
    private final WarehouseMapper warehouseMapper;
    private final WarehouseCriteriaRepository warehouseCriteriaRepository;
    private final AuthUtils authUtils;
    private final ElementCriteriaRepository elementCriteriaRepository;
    private final ElementInWarehouseService elementInWarehouseService;
    private final LocationRepository locationRepository;

    @Override
    public List<WarehouseDto> getAll() {
        return repository.findAllByDeletedIsFalse().stream()
                .map(warehouseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public WarehouseDto getById(Long id) {
        Warehouse warehouse = repository.findByIdAndDeletedIsFalse(id).orElseThrow(EntityNotFoundException::new);

        //Check if Warehouse is from user company
        if(!Objects.equals(warehouse.getCompany().getId(), authUtils.getLoggedUserCompanyId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

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
        Warehouse warehouse = repository.findById(id).orElseThrow(EntityNotFoundException::new);

        //Check if Warehouse is from user company
        if(!Objects.equals(warehouse.getCompany().getId(), authUtils.getLoggedUserCompanyId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        repository.deleteById(id);
    }

    @Override
    public WarehouseDto update(Long id, WarehouseWithLocationDto warehouseWithLocationDto) {
        Warehouse warehouse = repository.findByIdAndDeletedIsFalse(id).orElseThrow(EntityNotFoundException::new);

        //Check if Warehouse is from user company
        if(!Objects.equals(warehouse.getCompany().getId(), authUtils.getLoggedUserCompanyId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        warehouse.setName(warehouseWithLocationDto.getName());
        warehouse.setDescription(warehouseWithLocationDto.getDescription());
        warehouse.setOpeningHours(warehouseWithLocationDto.getOpeningHours());

        Location location = warehouse.getLocation();
        location.setXCoordinate(warehouseWithLocationDto.getXCoordinate());
        location.setYCoordinate(warehouseWithLocationDto.getYCoordinate());
        location.setCity(warehouseWithLocationDto.getCity());
        location.setStreet(warehouseWithLocationDto.getStreet());
        location.setPropertyNumber(warehouseWithLocationDto.getPropertyNumber());
        location.setApartmentNumber(warehouseWithLocationDto.getApartmentNumber());
        location.setZipCode(warehouseWithLocationDto.getZipCode());
        locationRepository.save(location);

        return warehouseMapper.toDto(repository.save(warehouse));
    }

    public List<WarehouseLocationDto> findAllWithFilters(WarehouseSearchCriteria warehouseSearchCriteria){
        return warehouseCriteriaRepository.findAllWithFilters(warehouseSearchCriteria);
    }

    @Override
    public WarehouseDto addWithWarehouseCount(WarehouseDto warehouseDto) {
        Company company = companyRepository.findByIdAndDeletedIsFalse(authUtils.getLoggedUserCompanyId()).orElseThrow(EntityNotFoundException::new);
        warehouseDto.setCompanyId(company.getId());
        warehouseDto.setElementInWarehouses(new ArrayList<>());
        warehouseDto.setTools(new ArrayList<>());
        Warehouse warehouse = repository.save(warehouseMapper.toEntity(warehouseDto));

        List<ElementDto> elementsToAdd = elementCriteriaRepository.findAllWithFilters(new ElementSearchCriteria());

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

    @Override
    public WarehouseDto addWarehouseWithLocation(WarehouseWithLocationDto warehouseWithLocationDto) {
        warehouseWithLocationDto.setCompanyId(authUtils.getLoggedUserCompanyId());

        Location location = locationRepository.save(
                Location.builder()
                        .xCoordinate(warehouseWithLocationDto.getXCoordinate())
                        .yCoordinate(warehouseWithLocationDto.getYCoordinate())
                        .city(warehouseWithLocationDto.getCity())
                        .street(warehouseWithLocationDto.getStreet())
                        .propertyNumber(warehouseWithLocationDto.getPropertyNumber())
                        .apartmentNumber(warehouseWithLocationDto.getApartmentNumber())
                        .zipCode(warehouseWithLocationDto.getZipCode())
                        .build()
        );

        return addWithWarehouseCount(
                WarehouseDto.builder()
                        .name(warehouseWithLocationDto.getName())
                        .description(warehouseWithLocationDto.getDescription())
                        .openingHours(warehouseWithLocationDto.getOpeningHours())
                        .locationId(location.getId())
                        .build());
    }
}
