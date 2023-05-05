package com.emontazysta.service.impl;

import com.emontazysta.mapper.ToolMapper;
import com.emontazysta.model.Tool;
import com.emontazysta.model.dto.ToolDto;
import com.emontazysta.model.dto.filterDto.ToolFilterDto;
import com.emontazysta.model.searchcriteria.ToolSearchCriteria;
import com.emontazysta.repository.ToolRepository;
import com.emontazysta.repository.criteria.ToolCriteriaRepository;
import com.emontazysta.service.ToolService;
import com.emontazysta.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ToolServiceImpl implements ToolService {

    private final ToolRepository repository;
    private final ToolCriteriaRepository toolCriteriaRepository;
    private final ToolMapper toolMapper;
    private final AuthUtils authUtils;

    @Override
    public List<ToolDto> getAll() {
        return repository.findAll().stream()
                .map(toolMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ToolDto getById(Long id) {
        Tool tool = repository.findById(id).orElseThrow(EntityNotFoundException::new);

        if(tool.getWarehouse().getCompany().getId().equals(authUtils.getLoggedUserCompanyId())) {
            return toolMapper.toDto(tool);
        }else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public ToolDto getByCode(String code) {
        Tool tool = repository.findByCode(code);
        if(tool == null) {
            throw new EntityNotFoundException();
        }else {
            if(tool.getWarehouse().getCompany().getId().equals(authUtils.getLoggedUserCompanyId())) {
                return toolMapper.toDto(tool);
            }else {
                throw new EntityNotFoundException();
            }
        }
    }

    @Override
    public ToolDto add(ToolDto toolDto) {
        toolDto.setCreatedAt(LocalDate.now());
        toolDto.setCode("T|"+UUID.randomUUID());
        toolDto.setToolReleases(new ArrayList<>());
        toolDto.setToolEvents(new ArrayList<>());

        return toolMapper.toDto(repository.save(toolMapper.toEntity(toolDto)));
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

    public List<ToolFilterDto> getTools(ToolSearchCriteria toolSearchCriteria, Principal principal){
        return toolCriteriaRepository.finadAllWithFilter(toolSearchCriteria, principal);
    }

}
