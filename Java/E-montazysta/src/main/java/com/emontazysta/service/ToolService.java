package com.emontazysta.service;

import com.emontazysta.model.dto.ToolDto;

import java.util.List;

public interface ToolService {

    List<ToolDto> getAll();
    ToolDto getById(Long id);
    ToolDto getByCode(String code);
    ToolDto add(ToolDto tool);
    void delete(Long id);
    ToolDto update(Long id, ToolDto tool);
}
