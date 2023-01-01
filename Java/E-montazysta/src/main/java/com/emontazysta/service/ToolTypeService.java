package com.emontazysta.service;

import com.emontazysta.model.ToolType;
import com.emontazysta.repository.ToolTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
public class ToolTypeService {

    private final ToolTypeRepository repository;

    public List<ToolType> getAll() {
        return repository.findAll();
    }

    public ToolType getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tool type with id " + id + " not found!"));

    }

    public void add(ToolType toolType) {
        repository.save(toolType);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
