package com.emontazysta.service.impl;

import com.emontazysta.mapper.ElementMapper;
import com.emontazysta.model.Element;
import com.emontazysta.model.dto.ElementDto;
import com.emontazysta.model.searchcriteria.ElementSearchCriteria;
import com.emontazysta.repository.ElementRepository;
import com.emontazysta.repository.criteria.ElementCriteriaRepository;
import com.emontazysta.service.ElementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ElementServiceImpl implements ElementService {

    private final ElementRepository repository;
    private final ElementMapper elementMapper;
    private final ElementCriteriaRepository elementCriteriaRepository;


    @Override
    public List<ElementDto> getAll() {
        return repository.findAllByDeletedIsFalse().stream()
                .map(elementMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ElementDto getById(Long id) {
        Element element = repository.findByIdAndDeletedIsFalse(id).orElseThrow(EntityNotFoundException::new);
        return elementMapper.toDto(element);
    }

    @Override
    public ElementDto getByCode(String code) {
        Element response = repository.findByCodeAndDeletedIsFalse(code).orElseThrow(EntityNotFoundException::new);
        return elementMapper.toDto(response);
    }

    @Override
    public ElementDto add(ElementDto elementDto) {
        Element element = elementMapper.toEntity(elementDto);
        element.setCode(UUID.randomUUID().toString());
        return elementMapper.toDto(repository.save(element));
    }

    @Override
    public void delete(Long id) {
        Element element = repository.findByIdAndDeletedIsFalse(id).orElseThrow(EntityNotFoundException::new);
        element.setDeleted(true);
        element.getElementReturnReleases().forEach(elementReturnRelease -> elementReturnRelease.setElement(null));
        element.getElementInWarehouses().forEach(elementInWarehouse -> elementInWarehouse.setElement(null));
        element.getElementEvents().forEach(elementEvent -> elementEvent.setElement(null));
        element.getAttachment().setElement(null);
        //TODO: missing part for ordersStages that should be changed to associated table
        repository.save(element);
    }

    @Override
    public ElementDto update(Long id, ElementDto elementDto) {

        Element updatedElement = elementMapper.toEntity(elementDto);
        Element element = repository.findByIdAndDeletedIsFalse(id).orElseThrow(EntityNotFoundException::new);
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
}
