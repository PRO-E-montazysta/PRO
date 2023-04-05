package com.emontazysta.service.impl;


import com.emontazysta.mapper.ElementEventMapper;
import com.emontazysta.model.ElementEvent;
import com.emontazysta.model.dto.ElementEventDto;
import com.emontazysta.repository.ElementEventRepository;
import com.emontazysta.service.ElementEventService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ElementEventServiceImpl implements ElementEventService {

    private final ElementEventRepository repository;
    private final ElementEventMapper elementEventMapper;

    @Override
    public List<ElementEventDto> getAll() {
        return repository.findAll().stream()
                .map(elementEventMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ElementEventDto getById(Long id){
        ElementEvent elementEvent = repository.findById(id).orElseThrow( () -> new RuntimeException("Element Event with id " + id +" not found"));
        return elementEventMapper.toDto(elementEvent);
    }

    @Override
    public ElementEventDto add(ElementEventDto event) {
        ElementEvent elementEvent = elementEventMapper.toEntity(event);
        return elementEventMapper.toDto(repository.save(elementEvent));
    }

    @Override
    public void delete(Long id) {

        repository.deleteById(id);
    }

    @Override
    public ElementEventDto update(Long id, ElementEventDto event) {
        ElementEvent updatedElementEvent = elementEventMapper.toEntity(event);
        ElementEvent elementEvent = repository.findById(id).orElseThrow( () -> new RuntimeException("Element Event with id " + id +" not found"));
        elementEvent.setEventDate(updatedElementEvent.getEventDate());
        elementEvent.setMovingDate(updatedElementEvent.getMovingDate());
        elementEvent.setCompletionDate(updatedElementEvent.getCompletionDate());
        elementEvent.setDescription(updatedElementEvent.getDescription());
        elementEvent.setStatus(updatedElementEvent.getStatus());
        elementEvent.setQuantity(updatedElementEvent.getQuantity());
        elementEvent.setAcceptedBy(updatedElementEvent.getAcceptedBy());
        elementEvent.setUpdatedBy(updatedElementEvent.getUpdatedBy());
        elementEvent.setElement(updatedElementEvent.getElement());
        elementEvent.setAttachments(elementEvent.getAttachments());
        return elementEventMapper.toDto(repository.save(elementEvent));
    }
}
