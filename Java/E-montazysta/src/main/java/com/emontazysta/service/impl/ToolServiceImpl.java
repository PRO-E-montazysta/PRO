package com.emontazysta.service.impl;

import com.emontazysta.model.Tool;
import com.emontazysta.repository.ToolRepository;
import com.emontazysta.service.ToolService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ToolServiceImpl implements ToolService {

    private final ToolRepository repository;

    @Override
    public List<Tool> getAll() {
        return repository.findAll();
    }

    @Override
    public Tool getById(Long id) {
        return repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Tool getByCode(String code) {
        return repository.findByCode(code);
    }

    @Override
    public void add(Tool tool) {
        repository.save(tool);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
