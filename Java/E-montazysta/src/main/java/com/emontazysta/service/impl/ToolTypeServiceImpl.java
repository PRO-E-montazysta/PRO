package com.emontazysta.service.impl;

import com.emontazysta.mapper.ToolTypeMapper;
import com.emontazysta.model.ToolType;
import com.emontazysta.model.dto.ToolTypeDto;
import com.emontazysta.model.searchcriteria.ToolTypeSearchCriteria;
import com.emontazysta.repository.ToolTypeRepository;
import com.emontazysta.repository.criteria.ToolTypeCriteriaRepository;
import com.emontazysta.service.ToolTypeService;
import com.emontazysta.util.AuthUtils;
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
    private final AuthUtils authUtils;

    @Override
    public List<ToolTypeDto> getAll() {
        return repository.findAll().stream()
                .map(toolTypeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ToolTypeDto getById(Long id) {
        ToolType toolType = repository.findById(id).orElseThrow(() -> new RuntimeException("Tool type with id " + id + " not found!"));
        return toolTypeMapper.toDto(toolType);

    }

    @Override
    public ToolTypeDto add(ToolTypeDto toolTypeDto) {
        toolTypeDto.setCompanyId(authUtils.getLoggedUserCompanyId());
        ToolType toolType = toolTypeMapper.toEntity(toolTypeDto);
        return toolTypeMapper.toDto(repository.save(toolType));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override

    public List<ToolTypeDto> findAllWithFilters(ToolTypeSearchCriteria toolTypeSearchCriteria) {
        return toolTypeCriteriaRepository.findAllWithFilters(toolTypeSearchCriteria);
    }

    public ToolTypeDto update(Long id, ToolTypeDto toolTypeDto) {
        ToolType updatedToolType = toolTypeMapper.toEntity(toolTypeDto);
        ToolType toolType = repository.findById(id).orElseThrow(() -> new RuntimeException("Tool type with id " + id + " not found!"));
        toolType.setName(updatedToolType.getName());
        toolType.setCriticalNumber(updatedToolType.getCriticalNumber());
        toolType.setAttachments(updatedToolType.getAttachments());
        toolType.setListOfToolsPlannedNumber(updatedToolType.getListOfToolsPlannedNumber());
        toolType.setTools(updatedToolType.getTools());
        return toolTypeMapper.toDto(repository.save(toolType));

    }
}
