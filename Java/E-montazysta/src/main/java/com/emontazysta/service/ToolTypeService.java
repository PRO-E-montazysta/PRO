package com.emontazysta.service;

import com.emontazysta.model.dto.ToolTypeDto;

import java.util.List;

public interface ToolTypeService {

    List<ToolTypeDto> getAll();
    ToolTypeDto getById(Long id);
    ToolTypeDto add(ToolTypeDto toolType);
    void delete(Long id);
}
