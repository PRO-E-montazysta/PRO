package com.emontazysta.service;

import com.emontazysta.model.Tool;

import java.util.List;

public interface ToolService {

    List<Tool> getAll();
    Tool getById(Long id);
    Tool getByCode(String code);
    void add(Tool tool);
    void delete(Long id);
    void update(Long id, Tool tool);
}
