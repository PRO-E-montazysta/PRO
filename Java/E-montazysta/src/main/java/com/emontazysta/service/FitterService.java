package com.emontazysta.service;

import com.emontazysta.model.dto.FitterDto;
import com.emontazysta.model.dto.WorkingOnDto;
import com.emontazysta.model.searchcriteria.AppUserSearchCriteria;

import java.security.Principal;
import java.util.List;

public interface FitterService {

    List<FitterDto> getAll(Principal principal);
    FitterDto getById(Long id);
    FitterDto add(FitterDto fitter);
    void delete(Long id);
    FitterDto update(Long id, FitterDto fitter);
    List<FitterDto> getAvailable(AppUserSearchCriteria appUserSearchCriteria, Principal principal);
    List<WorkingOnDto> getWorkingOn(Long id);
}
