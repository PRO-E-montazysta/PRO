package com.emontazysta.service.impl;


import com.emontazysta.model.ElementEvent;
import com.emontazysta.repository.ElementEventRepository;
import com.emontazysta.service.ElementEventService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ElementEventServiceImpl implements ElementEventService {

    private final ElementEventRepository repository;

    public List<ElementEvent> getAll() {
        return repository.findAll();
    }

    public ElementEvent getById(Long id){
        return repository.findById(id)
                .orElseThrow( () -> new RuntimeException("Element Event with id " + id +" not found"));
    }

    public void add(ElementEvent event) {
        repository.save(event);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
