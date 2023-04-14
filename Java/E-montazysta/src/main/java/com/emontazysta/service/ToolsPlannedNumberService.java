package com.emontazysta.service;

import com.emontazysta.model.dto.ToolsPlannedNumberDto;

import java.util.List;

public interface ToolsPlannedNumberService {

    List<ToolsPlannedNumberDto> getAll();
    ToolsPlannedNumberDto getById(Long id);
    ToolsPlannedNumberDto add(ToolsPlannedNumberDto toolsPlannedNumberDto);
    void delete(Long id);
    ToolsPlannedNumberDto update(Long id, ToolsPlannedNumberDto elementsPlannedNumberDto);
}
