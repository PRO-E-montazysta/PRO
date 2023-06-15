package com.emontazysta.service;

import com.emontazysta.model.dto.ForemanDto;
import com.emontazysta.model.dto.WorkingOnDto;

import java.security.Principal;
import java.util.List;

public interface ForemanService {

    List<ForemanDto> getAll(Principal principal);
    ForemanDto getById(Long id);
    ForemanDto add(ForemanDto foreman);
    void delete(Long id);
    ForemanDto update(Long id, ForemanDto foreman);
    List<WorkingOnDto> getWorkingOn(Long id);
}
