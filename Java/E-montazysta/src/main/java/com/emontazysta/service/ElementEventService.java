package com.emontazysta.service;

import com.emontazysta.model.dto.ElementEventDto;

import java.util.List;

public interface ElementEventService {

    List<ElementEventDto> getAll();
    ElementEventDto getById(Long id);
    ElementEventDto add(ElementEventDto event);
    void delete(Long id);
    ElementEventDto update(Long id, ElementEventDto event);
}
