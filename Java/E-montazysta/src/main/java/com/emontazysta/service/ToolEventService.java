package com.emontazysta.service;

import com.emontazysta.model.dto.ToolEventDto;

import java.util.List;

public interface ToolEventService {

    List<ToolEventDto> getAll();
    ToolEventDto getById(Long id);
    ToolEventDto add(ToolEventDto event);
    void delete(Long id);
}
