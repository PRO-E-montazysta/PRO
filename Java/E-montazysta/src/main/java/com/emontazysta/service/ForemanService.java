package com.emontazysta.service;

import com.emontazysta.model.dto.ForemanDto;

import java.util.List;

public interface ForemanService {

    List<ForemanDto> getAll();
    ForemanDto getById(Long id);
    ForemanDto add(ForemanDto foreman);
    void delete(Long id);
    ForemanDto update(Long id, ForemanDto foreman);
}
