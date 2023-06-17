package com.emontazysta.service;

import com.emontazysta.model.dto.ToolReleaseDto;

import java.util.List;

public interface ToolReleaseService {

    List<ToolReleaseDto> getAll();
    ToolReleaseDto getById(Long id);
    ToolReleaseDto add(ToolReleaseDto toolRelease);
    void delete(Long id);
    ToolReleaseDto update(Long id, ToolReleaseDto toolRelease);
    List<ToolReleaseDto> findAllFromCompany();
}
