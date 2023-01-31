package com.emontazysta.mapper;

import com.emontazysta.model.ElementReturnRelease;
import com.emontazysta.model.dto.ElementReturnReleaseDto;

public class ElementReturnReleaseMapper {

    public static ElementReturnReleaseDto elementReturnReleaseToDto(ElementReturnRelease elementReturnRelease) {
        return ElementReturnReleaseDto.builder()
                .id(elementReturnRelease.getId())
                .releaseTime(elementReturnRelease.getReleaseTime())
                .releasedQuantity(elementReturnRelease.getReleasedQuantity())
                .returnedQuantity(elementReturnRelease.getReturnedQuantity())
                .returnTime(elementReturnRelease.getReturnTime())
                .build();

    }
}
