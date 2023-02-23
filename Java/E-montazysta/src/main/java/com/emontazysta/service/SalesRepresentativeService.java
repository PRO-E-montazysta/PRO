package com.emontazysta.service;

import com.emontazysta.model.dto.SalesRepresentativeDto;

import java.util.List;

public interface SalesRepresentativeService {

    List<SalesRepresentativeDto> getAll();
    SalesRepresentativeDto getById(Long id);
    SalesRepresentativeDto add(SalesRepresentativeDto salesRepresentative);
    void delete(Long id);
}
