package com.emontazysta.service.impl;

import com.emontazysta.model.ToolEvent;
import com.emontazysta.repository.ToolEventRepository;
import com.emontazysta.service.ToolEventService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class ToolEventServiceImpl implements ToolEventService {

    private final ToolEventRepository repository;

    public List<ToolEvent> getAll() {
        return repository.findAll();
    }

    public ToolEvent getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tool event with id " + id + " not found!"));

    }

    public void add(ToolEvent event) {
        repository.save(event);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
