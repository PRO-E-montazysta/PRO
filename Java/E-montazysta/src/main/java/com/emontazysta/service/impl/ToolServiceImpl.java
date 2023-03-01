package com.emontazysta.service.impl;

import com.emontazysta.mapper.ToolMapper;
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
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ToolServiceImpl implements ToolService {

    private final ToolRepository repository;
    
    private final ToolCriteriaRepository toolCriteriaRepository;

    private final ToolMapper toolMapper;

    @Override
    public List<ToolDto> getAll() {
        return repository.findAll().stream()
                .map(toolMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ToolDto getById(Long id) {
        Tool tool = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return toolMapper.toDto(tool);
    }

    @Override
    public ToolDto getByCode(String code) {
        Tool response = repository.findByCode(code);
        if(response == null)
            throw new EntityNotFoundException();
        else
            return toolMapper.toDto(response);
    }

    @Override
    public ToolDto add(ToolDto toolDto) {
        Tool tool = toolMapper.toEntity(toolDto);
        tool.setCreatedAt(LocalDate.now());
        tool.setCode(UUID.randomUUID().toString());
        return toolMapper.toDto(repository.save(tool));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public ToolDto update(Long id, ToolDto toolDto) {

        Tool updatedTool = toolMapper.toEntity(toolDto);
        Tool tool = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        tool.setName(updatedTool.getName());
        tool.setToolReleases(updatedTool.getToolReleases());
        tool.setWarehouse(updatedTool.getWarehouse());
        tool.setToolEvents(updatedTool.getToolEvents());
        tool.setToolType(updatedTool.getToolType());

        return toolMapper.toDto(repository.save(tool));
    }

    public List<ToolDto> getTools(ToolSearchCriteria toolSearchCriteria){
        return toolCriteriaRepository.finadAllWithFilter(toolSearchCriteria);
    }

}
