package com.emontazysta.mapper;

import com.emontazysta.model.ElementReturnRelease;
import com.emontazysta.model.dto.ElementReturnReleaseDto;
import com.emontazysta.repository.DemandAdHocRepository;
import com.emontazysta.repository.ElementRepository;
import com.emontazysta.repository.ForemanRepository;
import com.emontazysta.repository.OrderStageRepository;
import com.emontazysta.repository.WarehousemanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
@RequiredArgsConstructor
public class ElementReturnReleaseMapper {

    private final WarehousemanRepository warehousemanRepository;
    private final ElementRepository elementRepository;
    private final DemandAdHocRepository demandAdHocRepository;
    private final ForemanRepository foremanRepository;
    private final OrderStageRepository orderStageRepository;

    public ElementReturnReleaseDto toDto(ElementReturnRelease elementReturnRelease) {
        return ElementReturnReleaseDto.builder()
                .id(elementReturnRelease.getId())
                .releaseTime(elementReturnRelease.getReleaseTime())
                .releasedQuantity(elementReturnRelease.getReleasedQuantity())
                .returnedQuantity(elementReturnRelease.getReturnedQuantity())
                .returnTime(elementReturnRelease.getReturnTime())
                .releasedById(elementReturnRelease.getReleasedBy() == null ? null : elementReturnRelease.getReleasedBy().getId())
                .elementId(elementReturnRelease.getElement() == null ? null : elementReturnRelease.getElement().getId())
                .demandAdHocId(elementReturnRelease.getDemandAdHoc() == null ? null : elementReturnRelease.getDemandAdHoc().getId())
                .orderStageId(elementReturnRelease.getOrderStage() == null ? null : elementReturnRelease.getOrderStage().getId())
                .deleted(elementReturnRelease.isDeleted())
                .build();
    }

    public ElementReturnRelease toEntity(ElementReturnReleaseDto elementReturnReleaseDto) {
        return ElementReturnRelease.builder()
                .id(elementReturnReleaseDto.getId())
                .releaseTime(elementReturnReleaseDto.getReleaseTime())
                .releasedQuantity(elementReturnReleaseDto.getReleasedQuantity())
                .returnedQuantity(elementReturnReleaseDto.getReturnedQuantity())
                .returnTime(elementReturnReleaseDto.getReturnTime())
                .releasedBy(elementReturnReleaseDto.getReleasedById() == null ? null : warehousemanRepository.findById(elementReturnReleaseDto.getReleasedById()).orElseThrow(EntityNotFoundException::new))
                .element(elementReturnReleaseDto.getElementId() == null ? null : elementRepository.findById(elementReturnReleaseDto.getElementId()).orElseThrow(EntityNotFoundException::new))
                .demandAdHoc(elementReturnReleaseDto.getDemandAdHocId() == null ? null : demandAdHocRepository.findById(elementReturnReleaseDto.getDemandAdHocId()).orElseThrow(EntityNotFoundException::new))
                .orderStage(elementReturnReleaseDto.getOrderStageId() == null ? null : orderStageRepository.findById(elementReturnReleaseDto.getOrderStageId()).orElseThrow(EntityNotFoundException::new))
                .build();
    }
}
