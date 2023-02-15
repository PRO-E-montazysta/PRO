package com.emontazysta.service;

import com.emontazysta.model.Tool;
import com.emontazysta.model.page.ToolPage;
import com.emontazysta.model.searchcriteria.ToolSearchCriteria;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ToolService {

    List<Tool> getAll();
    Tool getById(Long id);
    Tool getByCode(String code);
    void add(Tool tool);
    void delete(Long id);
    void update(Long id, Tool tool);
    Page<Tool> getTools(ToolPage toolPage, ToolSearchCriteria toolSearchCriteria);
}
