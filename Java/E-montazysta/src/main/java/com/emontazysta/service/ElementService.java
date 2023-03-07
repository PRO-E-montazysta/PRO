package com.emontazysta.service;

import com.emontazysta.model.dto.ElementDto;
import com.emontazysta.model.searchcriteria.ElementSearchCriteria;

import java.util.List;

public interface ElementService {

    List<ElementDto> getAll();
    ElementDto getById(Long id);
    ElementDto getByCode(String code);
    ElementDto add(ElementDto element);
    void delete(Long id);
    ElementDto update(Long id, ElementDto element);
    List<ElementDto> getFilteredElements(ElementSearchCriteria elementSearchCriteria);
}
