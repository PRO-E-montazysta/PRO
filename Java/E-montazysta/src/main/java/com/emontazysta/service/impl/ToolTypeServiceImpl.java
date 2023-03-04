package com.emontazysta.service.impl;

import com.emontazysta.mapper.ToolTypeMapper;
import com.emontazysta.model.ToolType;
import com.emontazysta.model.dto.ToolTypeDto;
import com.emontazysta.model.searchcriteria.ToolTypeSearchCriteria;
import com.emontazysta.repository.ToolTypeRepository;
import com.emontazysta.repository.criteria.ToolTypeCriteriaRepository;
import com.emontazysta.service.ToolTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ToolTypeServiceImpl implements ToolTypeService {

    private final ToolTypeRepository repository;
    private final ToolTypeMapper toolTypeMapper;
    private final ToolTypeCriteriaRepository toolTypeCriteriaRepository;

    public List<ToolTypeDto> getAll() {
        return repository.findAll().stream()
                .map(toolTypeMapper::toDto)
                .collect(Collectors.toList());
    }

    public ToolTypeDto getById(Long id) {
        ToolType toolType = repository.findById(id).orElseThrow(() -> new RuntimeException("Tool type with id " + id + " not found!"));
        return toolTypeMapper.toDto(toolType);

    }

    public ToolTypeDto add(ToolTypeDto toolTypeDto) {
        ToolType toolType = toolTypeMapper.toEntity(toolTypeDto);
        return toolTypeMapper.toDto(repository.save(toolType));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<ToolTypeDto> findAllWithFilters(ToolTypeSearchCriteria toolTypeSearchCriteria) {
        return toolTypeCriteriaRepository.findAllWithFilters(toolTypeSearchCriteria);
    }
}
