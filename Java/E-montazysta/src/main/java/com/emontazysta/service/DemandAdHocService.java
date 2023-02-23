package com.emontazysta.service;

import com.emontazysta.model.dto.DemandAdHocDto;

import java.util.List;

public interface DemandAdHocService {

    List<DemandAdHocDto> getAll();
    DemandAdHocDto getById(Long id);
    DemandAdHocDto add(DemandAdHocDto demandAdHoc);
    void delete(Long id);
    DemandAdHocDto update(Long id, DemandAdHocDto demandAdHoc);
}
