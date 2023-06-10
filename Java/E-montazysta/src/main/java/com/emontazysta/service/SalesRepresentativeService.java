package com.emontazysta.service;

import com.emontazysta.model.dto.SalesRepresentativeDto;

import java.security.Principal;
import java.util.List;

public interface SalesRepresentativeService {

    List<SalesRepresentativeDto> getAll(Principal principal);
    SalesRepresentativeDto getById(Long id);
    SalesRepresentativeDto add(SalesRepresentativeDto salesRepresentative);
    void delete(Long id);
    SalesRepresentativeDto update(Long id, SalesRepresentativeDto salesRepresentative);
}
