package com.emontazysta.mapper;

import com.emontazysta.model.DemandAdHoc;
import com.emontazysta.model.dto.DemandAdHocDto;

public class DemandAdHocMapper {

    public static DemandAdHocDto demandAdHocDtoDto(DemandAdHoc demandAdHoc) {
        return DemandAdHocDto.builder()
                .id(demandAdHoc.getId())
                .description(demandAdHoc.getDescription())
                .comments(demandAdHoc.getComments())
                .creationTime(demandAdHoc.getCreationTime())
                .readByWarehousemanTime(demandAdHoc.getReadByWarehousemanTime())
                .realisationTime(demandAdHoc.getRealisationTime())
                .warehousemanComment(demandAdHoc.getWarehousemanComment())
                .specialistComment(demandAdHoc.getSpecialistComment())
                .build();
    }
}
