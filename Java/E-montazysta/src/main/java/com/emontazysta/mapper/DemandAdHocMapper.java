package com.emontazysta.mapper;

import com.emontazysta.model.*;
import com.emontazysta.model.dto.DemandAdHocDto;
import com.emontazysta.model.dto.filterDto.DemandAdHocFilterDto;
import com.emontazysta.model.dto.filterDto.DemandAdHocWithToolsAndElementsDto;
import com.emontazysta.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DemandAdHocMapper {

    private final ToolReleaseRepository toolReleaseRepository;
    private final ElementReturnReleaseRepository elementReturnReleaseRepository;
    private final WarehouseManagerRepository warehouseManagerRepository;
    private final WarehousemanRepository warehousemanRepository;
    private final SpecialistRepository specialistRepository;
    private final ManagerRepository managerRepository;
    private final ForemanRepository foremanRepository;
    private final OrderStageRepository orderStageRepository;
    private final ToolsPlannedNumberRepository toolsPlannedNumberRepository;
    private final ToolsPlannedNumberMapper toolsPlannedNumberMapper;
    private final ElementsPlannedNumberRepository elementsPlannedNumberRepository;
    private final ElementsPlannedNumberMapper elementsPlannedNumberMapper;

    public DemandAdHocDto toDto(DemandAdHoc demandAdHoc) {
        return DemandAdHocDto.builder()
                .id(demandAdHoc.getId())
                .description(demandAdHoc.getDescription())
                .createdAt(demandAdHoc.getCreatedAt())
                .toolReleases(demandAdHoc.getToolReleases().stream()
                        .filter(toolRelease -> !toolRelease.isDeleted())
                        .map(ToolRelease::getId)
                        .collect(Collectors.toList()))
                .elementReturnReleases(demandAdHoc.getElementReturnReleases().stream()
                        .filter(elementReturnRelease -> !elementReturnRelease.isDeleted())
                        .map(ElementReturnRelease::getId)
                        .collect(Collectors.toList()))
                .warehouseManagerId(demandAdHoc.getWarehouseManager() == null ? null : demandAdHoc.getWarehouseManager().getId())
                .specialistId(demandAdHoc.getSpecialist() == null ? null : demandAdHoc.getSpecialist().getId())
                .createdById(demandAdHoc.getCreatedBy() == null ? null : demandAdHoc.getCreatedBy().getId())
                .orderStageId(demandAdHoc.getOrderStage() == null ? null : demandAdHoc.getOrderStage().getId())
                .listOfToolsPlannedNumber(demandAdHoc.getListOfToolsPlannedNumber().stream().map(ToolsPlannedNumber::getId).collect(Collectors.toList()))
                .listOfElementsPlannedNumber(demandAdHoc.getListOfElementsPlannedNumber().stream().map(ElementsPlannedNumber::getId).collect(Collectors.toList()))
                .build();
    }

    public DemandAdHoc toEntity(DemandAdHocDto demandAdHocDto) {

        List<ToolRelease> toolReleaseList = new ArrayList<>();
        demandAdHocDto.getToolReleases().forEach(toolReleaseId -> toolReleaseList.add(toolReleaseRepository.findById(toolReleaseId).orElseThrow(EntityNotFoundException::new)));

        List<ElementReturnRelease> elementReturnReleaseList = new ArrayList<>();
        demandAdHocDto.getElementReturnReleases().forEach(elementReturnReleaseId -> elementReturnReleaseList.add(elementReturnReleaseRepository.findById(elementReturnReleaseId).orElseThrow(EntityNotFoundException::new)));

        List<ToolsPlannedNumber> toolTypeList = new ArrayList<>();
        demandAdHocDto.getListOfToolsPlannedNumber().forEach(toolsPlannedNumberId -> toolTypeList.add(toolsPlannedNumberRepository.getReferenceById(toolsPlannedNumberId)));

        List<ElementsPlannedNumber> elementList = new ArrayList<>();
        demandAdHocDto.getListOfElementsPlannedNumber().forEach(elementsPlannedNumberId -> elementList.add(elementsPlannedNumberRepository.getReferenceById(elementsPlannedNumberId)));

        return DemandAdHoc.builder()
                .id(demandAdHocDto.getId())
                .description(demandAdHocDto.getDescription())
                .createdAt(demandAdHocDto.getCreatedAt())
                .toolReleases(toolReleaseList)
                .elementReturnReleases(elementReturnReleaseList)
                .warehouseManager(demandAdHocDto.getWarehouseManagerId() == null ? null : warehouseManagerRepository.getReferenceById(demandAdHocDto.getWarehouseManagerId()))
                .specialist(demandAdHocDto.getSpecialistId() == null ? null : specialistRepository.getReferenceById(demandAdHocDto.getSpecialistId()))
                .createdBy(demandAdHocDto.getCreatedById() == null ? null : foremanRepository.getReferenceById(demandAdHocDto.getCreatedById()))
                .orderStage(demandAdHocDto.getOrderStageId() == null ? null : orderStageRepository.getReferenceById(demandAdHocDto.getOrderStageId()))
                .listOfToolsPlannedNumber(toolTypeList)
                .listOfElementsPlannedNumber(elementList)
                .build();
    }

    public DemandAdHoc toEntity(DemandAdHocWithToolsAndElementsDto demandAdHocDto) {

        List<ToolRelease> toolReleaseList = new ArrayList<>();
        demandAdHocDto.getToolReleases().forEach(toolReleaseId -> toolReleaseList.add(toolReleaseRepository.getReferenceById(toolReleaseId)));

        List<ElementReturnRelease> elementReturnReleaseList = new ArrayList<>();
        demandAdHocDto.getElementReturnReleases().forEach(elementReturnReleaseId -> elementReturnReleaseList.add(elementReturnReleaseRepository.getReferenceById(elementReturnReleaseId)));

        List<ToolsPlannedNumber> toolTypeList = new ArrayList<>();
        demandAdHocDto.getListOfToolsPlannedNumber().forEach(toolsPlannedNumberDto -> toolTypeList.add(toolsPlannedNumberMapper.toEntity(toolsPlannedNumberDto)));

        List<ElementsPlannedNumber> elementList = new ArrayList<>();
        demandAdHocDto.getListOfElementsPlannedNumber().forEach(elementsPlannedNumberDto -> elementList.add(elementsPlannedNumberMapper.toEntity(elementsPlannedNumberDto)));

        return DemandAdHoc.builder()
                .id(demandAdHocDto.getId())
                .description(demandAdHocDto.getDescription())
                .createdAt(demandAdHocDto.getCreatedAt())
                .toolReleases(toolReleaseList)
                .elementReturnReleases(elementReturnReleaseList)
                .warehouseManager(demandAdHocDto.getWarehouseManagerId() == null ? null : warehouseManagerRepository.getReferenceById(demandAdHocDto.getWarehouseManagerId()))
                .specialist(demandAdHocDto.getSpecialistId() == null ? null : specialistRepository.getReferenceById(demandAdHocDto.getSpecialistId()))
                .createdBy(demandAdHocDto.getCreatedById() == null ? null : foremanRepository.getReferenceById(demandAdHocDto.getCreatedById()))
                .orderStage(demandAdHocDto.getOrderStageId() == null ? null : orderStageRepository.getReferenceById(demandAdHocDto.getOrderStageId()))
                .listOfToolsPlannedNumber(toolTypeList)
                .listOfElementsPlannedNumber(elementList)
                .build();
    }

    public DemandAdHocFilterDto toFilerDto(DemandAdHoc demandAdHoc) {
        return DemandAdHocFilterDto.builder()
                .id(demandAdHoc.getId())
                .description(demandAdHoc.getDescription())
                .createdAt(demandAdHoc.getCreatedAt())
                .createdByName(demandAdHoc.getCreatedBy().getFirstName() + " " + demandAdHoc.getCreatedBy().getLastName())
                .orderStageName(demandAdHoc.getOrderStage().getName())
                .build();
    }
}
