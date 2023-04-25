package com.emontazysta.mapper;

import com.emontazysta.model.ElementsPlannedNumber;
import com.emontazysta.model.dto.ElementsPlannedNumberDto;
import com.emontazysta.repository.ElementRepository;
import com.emontazysta.repository.OrderStageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
@RequiredArgsConstructor
public class ElementsPlannedNumberMapper {

    private final ElementRepository elementRepository;

    private final OrderStageRepository orderStageRepository;

    public ElementsPlannedNumberDto toDto (ElementsPlannedNumber elementsPlannedNumber) {
        return ElementsPlannedNumberDto.builder()
                .id(elementsPlannedNumber.getId())
                .numberOfElements(elementsPlannedNumber.getNumberOfElements())
                .elementId(elementsPlannedNumber.getElement() == null ? null : elementsPlannedNumber.getElement().getId())
                .orderStageId(elementsPlannedNumber.getOrderStage() == null ? null : elementsPlannedNumber.getOrderStage().getId())
                .build();
    }

    public ElementsPlannedNumber toEntity (ElementsPlannedNumberDto elementsPlannedNumberDto) {
        return ElementsPlannedNumber.builder()
                .id(elementsPlannedNumberDto.getId())
                .numberOfElements(elementsPlannedNumberDto.getNumberOfElements())
                .element(elementsPlannedNumberDto.getElementId() == null ? null : elementRepository.findById(elementsPlannedNumberDto.getElementId()).orElseThrow(EntityNotFoundException::new))
                .orderStage(elementsPlannedNumberDto.getOrderStageId() == null ? null : orderStageRepository.findById(elementsPlannedNumberDto.getOrderStageId()).orElseThrow(EntityNotFoundException::new))
                .build();
    }
}
