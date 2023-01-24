package com.emontazysta.service;

import com.emontazysta.model.ToolType;

import java.util.List;

public interface ToolTypeService {

    List<ToolType> getAll();
    ToolType getById(Long id);
    void add(ToolType toolType);
    void delete(Long id);
}
