package com.emontazysta.service;

import com.emontazysta.model.dto.ManagerDto;

import java.util.List;

public interface ManagerService {

    List<ManagerDto> getAll();
    ManagerDto getById(Long id);
    ManagerDto add(ManagerDto manager);
    void delete(Long id);
    ManagerDto update(Long id, ManagerDto manager);
}
