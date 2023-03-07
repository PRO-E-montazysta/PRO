package com.emontazysta.service;

import com.emontazysta.model.dto.FitterDto;

import java.util.List;

public interface FitterService {

    List<FitterDto> getAll();
    FitterDto getById(Long id);
    FitterDto add(FitterDto fitter);
    void delete(Long id);
    FitterDto update(Long id, FitterDto fitter);

}
