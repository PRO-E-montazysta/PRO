package com.emontazysta.service;

import com.emontazysta.model.dto.DemandAdHocDto;
import com.emontazysta.model.dto.filterDto.DemandAdHocFilterDto;
import com.emontazysta.model.searchcriteria.DemandAdHocSearchCriteria;
import java.util.List;

public interface DemandAdHocService {

    List<DemandAdHocDto> getAll();
    DemandAdHocDto getById(Long id);
    DemandAdHocDto add(DemandAdHocDto demandAdHoc);
    void delete(Long id);
    DemandAdHocDto update(Long id, DemandAdHocDto demandAdHoc);
    List<DemandAdHocFilterDto> getFiltered(DemandAdHocSearchCriteria demandAdHocSearchCriteria);
}
