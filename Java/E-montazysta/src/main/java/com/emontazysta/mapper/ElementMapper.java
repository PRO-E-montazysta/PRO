package com.emontazysta.mapper;

import com.emontazysta.model.Element;
import com.emontazysta.model.ElementEvent;
import com.emontazysta.model.ElementInWarehouse;
import com.emontazysta.model.ElementReturnRelease;
import com.emontazysta.model.ElementsPlannedNumber;
import com.emontazysta.model.dto.ElementDto;
import com.emontazysta.repository.AttachmentRepository;
import com.emontazysta.repository.ElementEventRepository;
import com.emontazysta.repository.ElementInWarehouseRepository;
import com.emontazysta.repository.ElementReturnReleaseRepository;
import com.emontazysta.repository.ElementsPlannedNumberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ElementMapper {

    private final ElementReturnReleaseRepository elementReturnReleaseRepository;
    private final ElementInWarehouseRepository elementInWarehouseRepository;
    private final ElementEventRepository elementEventRepository;
    private final AttachmentRepository attachmentRepository;
    private final ElementsPlannedNumberRepository elementsPlannedNumberRepository;

    public ElementDto toDto (Element element) {
        return ElementDto.builder()
                .id(element.getId())
                .name(element.getName())
                .code(element.getCode())
                .typeOfUnit(element.getTypeOfUnit())
                .quantityInUnit(element.getQuantityInUnit())
                .elementReturnReleases(element.getElementReturnReleases().stream().map(ElementReturnRelease::getId).collect(Collectors.toList()))
                .elementInWarehouses(element.getElementInWarehouses().stream().map(ElementInWarehouse::getId).collect(Collectors.toList()))
                .elementEvents(element.getElementEvents().stream().map(ElementEvent::getId).collect(Collectors.toList()))
                .attachmentId(element.getAttachment() == null ? null : element.getAttachment().getId())
                .ListOfElementsPlannedNumber(element.getListOfElementsPlannedNumber().stream().map(ElementsPlannedNumber::getId).collect(Collectors.toList()))
                .build();
    }

    public Element toEntity(ElementDto elementDto) {

        List<ElementReturnRelease> elementReturnReleaseList = new ArrayList<>();
        elementDto.getElementReturnReleases().forEach(elementReturnReleaseId -> elementReturnReleaseList.add(elementReturnReleaseRepository.getReferenceById(elementReturnReleaseId)));

        List<ElementInWarehouse> elementInWarehouseList = new ArrayList<>();
        elementDto.getElementInWarehouses().forEach(elementInWarehouseId -> elementInWarehouseList.add(elementInWarehouseRepository.getReferenceById(elementInWarehouseId)));

        List<ElementEvent> elementEventList = new ArrayList<>();
        elementDto.getElementEvents().forEach(elementEventId -> elementEventList.add(elementEventRepository.getReferenceById(elementEventId)));

        List<ElementsPlannedNumber> elementsPlannedNumberList = new ArrayList<>();
        elementDto.getListOfElementsPlannedNumber().forEach(elementsPlannedNumberId -> elementsPlannedNumberList.add(elementsPlannedNumberRepository.getReferenceById(elementsPlannedNumberId)));

        return Element.builder()
                .id(elementDto.getId())
                .name(elementDto.getName())
                .code(elementDto.getCode())
                .typeOfUnit(elementDto.getTypeOfUnit())
                .quantityInUnit(elementDto.getQuantityInUnit())
                .elementReturnReleases(elementReturnReleaseList)
                .elementInWarehouses(elementInWarehouseList)
                .elementEvents(elementEventList)
                .attachment(elementDto.getAttachmentId() == null ? null : attachmentRepository.getReferenceById(elementDto.getAttachmentId()))
                .listOfElementsPlannedNumber(elementsPlannedNumberList)
                .build();
    }
}
