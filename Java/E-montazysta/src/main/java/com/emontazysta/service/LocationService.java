package com.emontazysta.service;

import com.emontazysta.model.dto.LocationDto;

import java.util.List;

public interface LocationService {

    List<LocationDto> getAll();
    LocationDto getById(Long id);
    LocationDto add(LocationDto location);
    void delete(Long id);
    LocationDto update(Long id, LocationDto location);
}
