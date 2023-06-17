package com.emontazysta.service;

import com.emontazysta.model.dto.ElementReturnReleaseDto;

import java.util.List;

public interface ElementReturnReleaseService {

    List<ElementReturnReleaseDto> getAll();
    ElementReturnReleaseDto getById(Long id);
    ElementReturnReleaseDto add(ElementReturnReleaseDto elementReturnRelease);
    void delete(Long id);
    ElementReturnReleaseDto update(Long id, ElementReturnReleaseDto elementReturnRelease);

    List<ElementReturnReleaseDto> findAllForCompany();
}
