package com.emontazysta.service;

import com.emontazysta.model.Tool;
import com.emontazysta.model.dto.ToolDto;
import com.emontazysta.model.searchcriteria.ToolReleaseSearchCriteria;
import com.emontazysta.model.searchcriteria.ToolSearchCriteria;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ToolService {

    List<ToolDto> getAll();
    ToolDto getById(Long id);
    ToolDto getByCode(String code);
    ToolDto add(ToolDto tool);
    void delete(Long id);
    List<ToolDto> getTools(ToolSearchCriteria toolSearchCriteria);
    ToolDto update(Long id, ToolDto tool);
}
