package com.emontazysta.service;

import com.emontazysta.model.dto.DemandAdHocDto;
import com.emontazysta.model.dto.filterDto.DemandAdHocFilterDto;
import com.emontazysta.model.dto.filterDto.DemandAdHocWithToolsAndElementsDto;
import com.emontazysta.model.searchcriteria.DemandAdHocSearchCriteria;
import javax.transaction.Transactional;
import java.util.List;

public interface DemandAdHocService {

    List<DemandAdHocDto> getAll();
    DemandAdHocDto getById(Long id);
    DemandAdHocDto add(DemandAdHocDto demandAdHoc);
    @Transactional
    DemandAdHocDto addWithToolsAndElements(DemandAdHocWithToolsAndElementsDto demandAdHoc);
    void delete(Long id);
    DemandAdHocDto update(Long id, DemandAdHocWithToolsAndElementsDto demandAdHoc);
    List<DemandAdHocFilterDto> getFiltered(DemandAdHocSearchCriteria demandAdHocSearchCriteria);
}
