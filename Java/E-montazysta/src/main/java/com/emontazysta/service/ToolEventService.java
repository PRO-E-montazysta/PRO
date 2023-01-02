package com.emontazysta.service;

import com.emontazysta.model.ToolEvent;
import com.emontazysta.repository.ToolEventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class ToolEventService {

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
