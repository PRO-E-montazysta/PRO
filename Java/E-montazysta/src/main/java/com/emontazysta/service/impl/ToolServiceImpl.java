package com.emontazysta.service.impl;

import com.emontazysta.model.Tool;
import com.emontazysta.model.dto.ToolDto;
import com.emontazysta.model.searchcriteria.ToolReleaseSearchCriteria;
import com.emontazysta.model.searchcriteria.ToolSearchCriteria;
import com.emontazysta.repository.ToolRepository;
import com.emontazysta.repository.criteria.ToolCriteriaRepository;
import com.emontazysta.service.ToolService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ToolServiceImpl implements ToolService {

    private final ToolRepository repository;
    private final ToolCriteriaRepository toolCriteriaRepository;

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
        tool.setCode(UUID.randomUUID().toString());
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

    public List<ToolDto> getTools(ToolSearchCriteria toolSearchCriteria){
        return toolCriteriaRepository.finadAllWithFilter(toolSearchCriteria);
    }

}
