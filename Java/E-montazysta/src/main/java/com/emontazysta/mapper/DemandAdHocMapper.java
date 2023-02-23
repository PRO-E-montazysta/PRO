package com.emontazysta.mapper;

import com.emontazysta.model.DemandAdHoc;
import com.emontazysta.model.ElementReturnRelease;
import com.emontazysta.model.OrderStage;
import com.emontazysta.model.ToolRelease;
import com.emontazysta.model.dto.DemandAdHocDto;
import com.emontazysta.repository.ElementReturnReleaseRepository;
import com.emontazysta.repository.ForemanRepository;
import com.emontazysta.repository.ManagerRepository;
import com.emontazysta.repository.OrderStageRepository;
import com.emontazysta.repository.SpecialistRepository;
import com.emontazysta.repository.ToolReleaseRepository;
import com.emontazysta.repository.WarehouseManagerRepository;
import com.emontazysta.repository.WarehousemanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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

    public DemandAdHocDto toDto(DemandAdHoc demandAdHoc) {
        return DemandAdHocDto.builder()
                .id(demandAdHoc.getId())
                .description(demandAdHoc.getDescription())
                .comments(demandAdHoc.getComments())
                .creationTime(demandAdHoc.getCreationTime())
                .readByWarehousemanTime(demandAdHoc.getReadByWarehousemanTime())
                .realisationTime(demandAdHoc.getRealisationTime())
                .warehousemanComment(demandAdHoc.getWarehousemanComment())
                .specialistComment(demandAdHoc.getSpecialistComment())
                .toolReleases(demandAdHoc.getToolReleases().stream().map(ToolRelease::getId).collect(Collectors.toList()))
                .elementReturnReleases(demandAdHoc.getElementReturnReleases().stream().map(ElementReturnRelease::getId).collect(Collectors.toList()))
                .warehouseManagerId(demandAdHoc.getWarehouseManager() == null ? null : demandAdHoc.getWarehouseManager().getId())
                .warehousemanId(demandAdHoc.getWarehouseman() == null ? null : demandAdHoc.getWarehouseman().getId())
                .specialistId(demandAdHoc.getSpecialist() == null ? null : demandAdHoc.getSpecialist().getId())
                .managerId(demandAdHoc.getManager() == null ? null : demandAdHoc.getManager().getId())
                .foremanId(demandAdHoc.getForeman() == null ? null : demandAdHoc.getForeman().getId())
                .ordersStages(demandAdHoc.getOrdersStages().stream().map(OrderStage::getId).collect(Collectors.toList()))
                .build();
    }

    public DemandAdHoc toEntity(DemandAdHocDto demandAdHocDto) {

        List<ToolRelease> toolReleaseList = new ArrayList<>();
        demandAdHocDto.getToolReleases().forEach(toolReleaseId -> toolReleaseList.add(toolReleaseRepository.getReferenceById(toolReleaseId)));

        List<ElementReturnRelease> elementReturnReleaseList = new ArrayList<>();
        demandAdHocDto.getElementReturnReleases().forEach(elementReturnReleaseId -> elementReturnReleaseList.add(elementReturnReleaseRepository.getReferenceById(elementReturnReleaseId)));

        List<OrderStage> orderStageList = new ArrayList<>();
        demandAdHocDto.getOrdersStages().forEach(orderStageId -> orderStageList.add(orderStageRepository.getReferenceById(orderStageId)));

        return DemandAdHoc.builder()
                .id(demandAdHocDto.getId())
                .description(demandAdHocDto.getDescription())
                .comments(demandAdHocDto.getComments())
                .creationTime(demandAdHocDto.getCreationTime())
                .readByWarehousemanTime(demandAdHocDto.getReadByWarehousemanTime())
                .realisationTime(demandAdHocDto.getRealisationTime())
                .warehousemanComment(demandAdHocDto.getWarehousemanComment())
                .specialistComment(demandAdHocDto.getSpecialistComment())
                .toolReleases(toolReleaseList)
                .elementReturnReleases(elementReturnReleaseList)
                .warehouseManager(demandAdHocDto.getWarehouseManagerId() == null ? null : warehouseManagerRepository.getReferenceById(demandAdHocDto.getWarehouseManagerId()))
                .warehouseman(demandAdHocDto.getWarehousemanId() == null ? null : warehousemanRepository.getReferenceById(demandAdHocDto.getWarehousemanId()))
                .specialist(demandAdHocDto.getSpecialistId() == null ? null : specialistRepository.getReferenceById(demandAdHocDto.getSpecialistId()))
                .manager(demandAdHocDto.getManagerId() == null ? null : managerRepository.getReferenceById(demandAdHocDto.getManagerId()))
                .foreman(demandAdHocDto.getForemanId() == null ? null : foremanRepository.getReferenceById(demandAdHocDto.getForemanId()))
                .ordersStages(orderStageList)
                .build();
    }
}
