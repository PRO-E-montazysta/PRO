package com.emontazysta.service;

import com.emontazysta.model.dto.UnavailabilityDto;

import java.util.List;

public interface UnavailabilityService {

    List<UnavailabilityDto> getAll();
    UnavailabilityDto getById(Long id);
    UnavailabilityDto add(UnavailabilityDto unavailability);
    void delete(Long id);
    UnavailabilityDto update(Long id, UnavailabilityDto unavailability);
}
