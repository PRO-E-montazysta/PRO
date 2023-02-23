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
                .servedById(elementReturnRelease.getServedBy() == null ? null : elementReturnRelease.getServedBy().getId())
                .elementId(elementReturnRelease.getElement() == null ? null : elementReturnRelease.getElement().getId())
                .demandAdHocId(elementReturnRelease.getDemandAdHoc() == null ? null : elementReturnRelease.getDemandAdHoc().getId())
                .foremanId(elementReturnRelease.getForeman() == null ? null : elementReturnRelease.getForeman().getId())
                .orderStageId(elementReturnRelease.getOrderStage() == null ? null : elementReturnRelease.getOrderStage().getId())
                .build();
    }

    public ElementReturnRelease toEntity(ElementReturnReleaseDto elementReturnReleaseDto) {
        return ElementReturnRelease.builder()
                .id(elementReturnReleaseDto.getId())
                .releaseTime(elementReturnReleaseDto.getReleaseTime())
                .releasedQuantity(elementReturnReleaseDto.getReleasedQuantity())
                .returnedQuantity(elementReturnReleaseDto.getReturnedQuantity())
                .returnTime(elementReturnReleaseDto.getReturnTime())
                .servedBy(elementReturnReleaseDto.getServedById() == null ? null : warehousemanRepository.getReferenceById(elementReturnReleaseDto.getServedById()))
                .element(elementReturnReleaseDto.getElementId() == null ? null : elementRepository.getReferenceById(elementReturnReleaseDto.getElementId()))
                .demandAdHoc(elementReturnReleaseDto.getDemandAdHocId() == null ? null : demandAdHocRepository.getReferenceById(elementReturnReleaseDto.getDemandAdHocId()))
                .foreman(elementReturnReleaseDto.getForemanId() == null ? null : foremanRepository.getReferenceById(elementReturnReleaseDto.getForemanId()))
                .orderStage(elementReturnReleaseDto.getOrderStageId() == null ? null : orderStageRepository.getReferenceById(elementReturnReleaseDto.getOrderStageId()))
                .build();
    }
}
