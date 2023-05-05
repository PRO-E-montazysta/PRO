package com.emontazysta.service;

import com.emontazysta.model.dto.ToolsPlannedNumberDto;
import com.emontazysta.model.dto.ToolsDtoPlannedNumberDto;

import java.util.List;

public interface ToolsPlannedNumberService {

    List<ToolsPlannedNumberDto> getAll();
    ToolsDtoPlannedNumberDto getById(Long id);
    ToolsPlannedNumberDto add(ToolsPlannedNumberDto toolsPlannedNumberDto);
    void delete(Long id);
    ToolsPlannedNumberDto update(Long id, ToolsPlannedNumberDto elementsPlannedNumberDto);
}
