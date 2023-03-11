package com.emontazysta.service;

import com.emontazysta.model.dto.SpecialistDto;

import java.util.List;

public interface SpecialistService {

    List<SpecialistDto> getAll();
    SpecialistDto getById(Long id);
    SpecialistDto add(SpecialistDto specialist);
    void delete(Long id);
    SpecialistDto update(Long id, SpecialistDto specialist);
}
