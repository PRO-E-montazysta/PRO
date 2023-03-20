package com.emontazysta.service;

import com.emontazysta.model.dto.ToolDto;
import com.emontazysta.model.dto.filterDto.ToolFilterDto;
import com.emontazysta.model.searchcriteria.ToolSearchCriteria;

import java.security.Principal;
import java.util.List;

public interface ToolService {

    List<ToolDto> getAll();
    ToolDto getById(Long id);
    ToolDto getByCode(String code);
    ToolDto add(ToolDto tool);
    void delete(Long id);
    List<ToolFilterDto> getTools(ToolSearchCriteria toolSearchCriteria, Principal principal);
    ToolDto update(Long id, ToolDto tool);
}
