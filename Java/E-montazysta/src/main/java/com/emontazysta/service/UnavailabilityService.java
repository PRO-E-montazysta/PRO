package com.emontazysta.service;

import com.emontazysta.model.dto.UnavailabilityDto;
import com.emontazysta.model.dto.UnavailabilityWithLocalDateDto;
import com.emontazysta.model.dto.filterDto.UnavailabilityFilterDto;
import com.emontazysta.model.searchcriteria.UnavailabilitySearchCriteria;

import java.util.List;

public interface UnavailabilityService {

    List<UnavailabilityDto> getAll();
    UnavailabilityDto getById(Long id);
    UnavailabilityDto add(UnavailabilityDto unavailability);
    UnavailabilityDto addWithLocalDate(UnavailabilityWithLocalDateDto unavailability);
    void delete(Long id);
    UnavailabilityDto update(Long id, UnavailabilityDto unavailability);
    UnavailabilityDto updateWithLocalDate(Long id, UnavailabilityWithLocalDateDto unavailability);
    List <UnavailabilityFilterDto> findAllWithFilters(UnavailabilitySearchCriteria unavailabilitySearchCriteria);
}
