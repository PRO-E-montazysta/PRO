package com.emontazysta.service.impl;

import com.emontazysta.mapper.ToolsPlannedNumberMapper;
import com.emontazysta.model.ToolsPlannedNumber;
import com.emontazysta.model.dto.ToolsPlannedNumberDto;
import com.emontazysta.model.dto.ToolsDtoPlannedNumberDto;
import com.emontazysta.repository.ToolsPlannedNumberRepository;
import com.emontazysta.service.ToolsPlannedNumberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ToolsPlannedNumberServiceImpl implements ToolsPlannedNumberService {

    private final ToolsPlannedNumberRepository toolsPlannedNumberRepository;
    private final ToolsPlannedNumberMapper toolsPlannedNumberMapper;
    @Override
    public List<ToolsPlannedNumberDto> getAll() {
        return toolsPlannedNumberRepository.findAll().stream()
                .map(toolsPlannedNumberMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ToolsDtoPlannedNumberDto getById(Long id) {
        ToolsPlannedNumber toolsPlannedNumber = toolsPlannedNumberRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return toolsPlannedNumberMapper.toDtoWithToolTypeDto(toolsPlannedNumber);
    }

    @Override
    public ToolsPlannedNumberDto add(ToolsPlannedNumberDto toolsPlannedNumberDto) {
        ToolsPlannedNumber toolsPlannedNumber = toolsPlannedNumberRepository.save(toolsPlannedNumberMapper.toEntity(toolsPlannedNumberDto));
        return toolsPlannedNumberMapper.toDto(toolsPlannedNumber);
    }

    @Override
    public void delete(Long id) {
        toolsPlannedNumberRepository.deleteById(id);
    }

    @Override
    public ToolsPlannedNumberDto update(Long id, ToolsPlannedNumberDto toolsPlannedNumberDto) {
        ToolsPlannedNumber updatedToolsPlannedNumber = toolsPlannedNumberMapper.toEntity(toolsPlannedNumberDto);
        ToolsPlannedNumber toolsPlannedNumber = toolsPlannedNumberRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        toolsPlannedNumber.setNumberOfTools(updatedToolsPlannedNumber.getNumberOfTools());
        toolsPlannedNumber.setToolType(updatedToolsPlannedNumber.getToolType());
        toolsPlannedNumber.setOrderStage(updatedToolsPlannedNumber.getOrderStage());
        return toolsPlannedNumberMapper.toDto(toolsPlannedNumberRepository.save(toolsPlannedNumber));
    }
}
