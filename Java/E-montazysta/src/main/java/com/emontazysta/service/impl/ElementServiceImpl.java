package com.emontazysta.service.impl;

import com.emontazysta.mapper.ElementInWarehouseMapper;
import com.emontazysta.mapper.ElementMapper;
import com.emontazysta.model.Element;
import com.emontazysta.model.ElementInWarehouse;
import com.emontazysta.model.dto.ElementDto;
import com.emontazysta.model.dto.ElementInWarehouseDto;
import com.emontazysta.model.dto.WarehouseDto;
import com.emontazysta.model.dto.WarehouseLocationDto;
import com.emontazysta.model.searchcriteria.ElementSearchCriteria;
import com.emontazysta.model.searchcriteria.WarehouseSearchCriteria;
import com.emontazysta.repository.ElementRepository;
import com.emontazysta.repository.criteria.ElementCriteriaRepository;
import com.emontazysta.service.ElementInWarehouseService;
import com.emontazysta.service.ElementService;
import com.emontazysta.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ElementServiceImpl implements ElementService {

    private final ElementRepository repository;
    private final ElementMapper elementMapper;
    private final ElementCriteriaRepository elementCriteriaRepository;
    private final WarehouseService warehouseService;
    private final ElementInWarehouseService elementInWarehouseService;
    private final ElementInWarehouseMapper elementInWarehouseMapper;


    @Override
    public List<ElementDto> getAll() {
        return repository.findAll().stream()
                .map(elementMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ElementDto getById(Long id) {
        Element element = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return elementMapper.toDto(element);
    }

    @Override
    public ElementDto getByCode(String code) {
        Element response = repository.findByCode(code);
        if(response == null)
            throw new EntityNotFoundException();
        else
            return elementMapper.toDto(response);
    }

    @Override
    public ElementDto add(ElementDto elementDto) {
        Element element = elementMapper.toEntity(elementDto);
        element.setCode("E|"+UUID.randomUUID());
        return elementMapper.toDto(repository.save(element));
    }

    @Override
    public void delete(Long id) {
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
        element.setOrdersStages(updatedElement.getOrdersStages());
        return elementMapper.toDto(repository.save(element));
    }

    @Override
    public List<ElementDto> getFilteredElements(ElementSearchCriteria elementSearchCriteria) {
        return elementCriteriaRepository.findAllWithFilters(elementSearchCriteria);
    }

    @Override
    public ElementDto addWithWarehouseCount(ElementDto elementDto) {
        elementDto.setCode(UUID.randomUUID().toString());
        Element element = repository.save(elementMapper.toEntity(elementDto));

        List<WarehouseLocationDto> warehousesToAdd = warehouseService.findAllWithFilters(new WarehouseSearchCriteria());

        warehousesToAdd.forEach(warehouseLocationDto -> {
            ElementInWarehouseDto elementInWarehouseDto = ElementInWarehouseDto.builder()
                    .inWarehouseCount(0)
                    .inUnitCount(0)
                    .rack("")
                    .shelf("")
                    .elementId(element.getId())
                    .warehouseId(warehouseLocationDto.getId())
                    .build();

            elementInWarehouseService.add(elementInWarehouseDto);
        });

        return elementMapper.toDto(element);
    }
}
