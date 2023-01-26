package com.emontazysta.service;

import com.emontazysta.model.ToolEvent;

import java.util.List;

public interface ToolEventService {

    List<ToolEvent> getAll();
    ToolEvent getById(Long id);
    void add(ToolEvent event);
    void delete(Long id);
}
