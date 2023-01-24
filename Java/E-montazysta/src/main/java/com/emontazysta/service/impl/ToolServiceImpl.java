package com.emontazysta.service.impl;

import com.emontazysta.model.Tool;
import com.emontazysta.repository.ToolRepository;
import com.emontazysta.service.ToolService;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
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
        Tool response = repository.findByCode(code);
        if(response == null)
            throw new EntityNotFoundException();
        else
            return response;
    }

    @Override
    public void add(Tool tool) {
        tool.setCreatedAt(new Date());
        repository.save(tool);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void update(Long id, Tool tool) {
        Tool updatedTool = this.getById(id);
        updatedTool.setName(tool.getName());

        repository.save(updatedTool);
    }
}
