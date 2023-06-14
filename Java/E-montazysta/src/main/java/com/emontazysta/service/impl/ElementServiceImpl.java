package com.emontazysta.service.impl;

import com.emontazysta.mapper.ElementInWarehouseMapper;
import com.emontazysta.mapper.ElementMapper;
import com.emontazysta.model.Element;
import com.emontazysta.model.ElementInWarehouse;
import com.emontazysta.model.dto.ElementDto;
import com.emontazysta.model.dto.ElementInWarehouseDto;
import com.emontazysta.model.dto.WarehouseLocationDto;
import com.emontazysta.model.searchcriteria.ElementSearchCriteria;
import com.emontazysta.model.searchcriteria.WarehouseSearchCriteria;
import com.emontazysta.repository.ElementInWarehouseRepository;
import com.emontazysta.repository.ElementRepository;
import com.emontazysta.repository.criteria.ElementCriteriaRepository;
import com.emontazysta.repository.criteria.WarehouseCriteriaRepository;
import com.emontazysta.service.ElementService;
import com.emontazysta.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ElementServiceImpl implements ElementService {

    private final ElementRepository repository;
    private final ElementMapper elementMapper;
    private final ElementCriteriaRepository elementCriteriaRepository;
    private final WarehouseCriteriaRepository warehouseCriteriaRepository;
    private final ElementInWarehouseRepository elementInWarehouseRepository;
    private final ElementInWarehouseMapper elementInWarehouseMapper;
    private final AuthUtils authUtils;


    @Override
    public List<ElementDto> getAll() {
        return repository.findAll().stream()
                .map(elementMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ElementDto getById(Long id) {
        Element element = repository.findById(id).orElseThrow(EntityNotFoundException::new);

        if(element == null) {
            throw new EntityNotFoundException();
        }else {
            if(element.getElementInWarehouses().get(0).getWarehouse().getCompany().getId().equals(authUtils.getLoggedUserCompanyId())) {
                return elementMapper.toDto(element);
            }else {
                throw new EntityNotFoundException();
            }
        }
    }

    @Override
    public ElementDto getByCode(String code) {
        Optional<Element> element = repository.findByCode(code);

        if(element.isEmpty()) {
            throw new EntityNotFoundException();
        }else {
            if(element.get().getElementInWarehouses().get(0).getWarehouse().getCompany().getId().equals(authUtils.getLoggedUserCompanyId())) {
                return elementMapper.toDto(element.get());
            }else {
                throw new EntityNotFoundException();
            }
        }
    }

    @Override
    public ElementDto add(ElementDto elementDto) {
        Element element = elementMapper.toEntity(elementDto);
        element.setCode("E|"+UUID.randomUUID());
        return elementMapper.toDto(repository.save(element));
    }

    @Override
    public void delete(Long id) {
        Element element = repository.findById(id).orElseThrow(EntityNotFoundException::new);

        //Check if Element is from user company
        if(!element.getElementInWarehouses().get(0).getWarehouse().getCompany().getId().equals(authUtils.getLoggedUserCompanyId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        //Set deleted flag for ElementInWarehouse from warehouse
        for(ElementInWarehouse elementInWarehouse : element.getElementInWarehouses()) {
            elementInWarehouse.setDeleted(true);
        }

        repository.deleteById(id);
    }

    @Override
    public ElementDto update(Long id, ElementDto elementDto) {

        Element updatedElement = elementMapper.toEntity(elementDto);
        Element element = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        element.setName(updatedElement.getName());
        element.setTypeOfUnit(updatedElement.getTypeOfUnit());
        element.setQuantityInUnit(updatedElement.getQuantityInUnit());
        element.setElementReturnReleases(updatedElement.getElementReturnReleases());
        element.setElementInWarehouses(updatedElement.getElementInWarehouses());
        element.setElementEvents(updatedElement.getElementEvents());
        element.setAttachment(updatedElement.getAttachment());
        element.setListOfElementsPlannedNumber(updatedElement.getListOfElementsPlannedNumber());
        return elementMapper.toDto(repository.save(element));
    }

    @Override
    public List<ElementDto> getFilteredElements(ElementSearchCriteria elementSearchCriteria) {
        return elementCriteriaRepository.findAllWithFilters(elementSearchCriteria);
    }

    @Override
    public ElementDto addWithWarehouseCount(ElementDto elementDto) {
        List<WarehouseLocationDto> warehousesToAdd = warehouseCriteriaRepository.findAllWithFilters(new WarehouseSearchCriteria());
        if(warehousesToAdd.size() == 0)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        elementDto.setCode("E|"+UUID.randomUUID());
        elementDto.setElementReturnReleases(new ArrayList<>());
        elementDto.setElementInWarehouses(new ArrayList<>());
        elementDto.setElementEvents(new ArrayList<>());
        elementDto.setListOfElementsPlannedNumber(new ArrayList<>());
        Element element = repository.save(elementMapper.toEntity(elementDto));

        warehousesToAdd.forEach(warehouseLocationDto -> {
            ElementInWarehouseDto elementInWarehouseDto = ElementInWarehouseDto.builder()
                    .inWarehouseCount(0)
                    .rack("")
                    .shelf("")
                    .elementId(element.getId())
                    .warehouseId(warehouseLocationDto.getId())
                    .build();

            elementInWarehouseRepository.save(elementInWarehouseMapper.toEntity(elementInWarehouseDto));
        });

        return elementMapper.toDto(element);
    }
}
