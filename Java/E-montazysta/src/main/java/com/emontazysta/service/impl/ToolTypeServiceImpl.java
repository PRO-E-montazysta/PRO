package com.emontazysta.service.impl;

import com.emontazysta.mapper.ToolTypeMapper;
import com.emontazysta.model.Tool;
import com.emontazysta.model.ToolType;
import com.emontazysta.model.dto.ToolTypeDto;
import com.emontazysta.model.searchcriteria.ToolTypeSearchCriteria;
import com.emontazysta.repository.ToolTypeRepository;
import com.emontazysta.repository.criteria.ToolTypeCriteriaRepository;
import com.emontazysta.service.ToolTypeService;
import com.emontazysta.util.AuthUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
        toolTypeDto.setAttachments(new ArrayList<>());
        toolTypeDto.setListOfToolsPlannedNumber(new ArrayList<>());
        toolTypeDto.setTools(new ArrayList<>());
        ToolType toolType = toolTypeMapper.toEntity(toolTypeDto);
        return toolTypeMapper.toDto(repository.save(toolType));
    }

    @Override
    public void delete(Long id) {
        ToolType toolType = repository.findById(id).orElseThrow(EntityNotFoundException::new);

        //Check if ToolType is from user company
        if(!Objects.equals(toolType.getCompany().getId(), authUtils.getLoggedUserCompanyId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        //Set deleted flag for Tools of deleted ToolType
        for(Tool tool : toolType.getTools()) {
            tool.setDeleted(true);
        }

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
