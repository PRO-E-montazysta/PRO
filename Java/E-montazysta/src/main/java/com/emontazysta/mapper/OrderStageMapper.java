package com.emontazysta.mapper;

import com.emontazysta.model.*;
import com.emontazysta.model.dto.OrderStageDto;
import com.emontazysta.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderStageMapper {

    private final FitterRepository fitterRepository;
    private final ForemanRepository foremanRepository;
    private final CommentRepository commentRepository;
    private final ToolReleaseRepository toolReleaseRepository;
    private final ElementReturnReleaseRepository elementReturnReleaseRepository;
    private final OrderRepository orderRepository;
    private final AttachmentRepository attachmentRepository;
    private final NotificationRepository notificationRepository;
    private final DemandAdHocRepository demandAdHocRepository;
    private final ToolsPlannedNumberRepository toolsPlannedNumberRepository;
    private final ElementsPlannedNumberRepository elementsPlannedNumberRepository;

    public OrderStageDto toDto(OrderStage orderStage) {

        return OrderStageDto.builder()
                .id(orderStage.getId())
                .name(orderStage.getName())
                .status(orderStage.getStatus())
                .price(orderStage.getPrice())
                .plannedStartDate(orderStage.getPlannedStartDate())
                .plannedEndDate(orderStage.getPlannedEndDate())
                .startDate(orderStage.getStartDate())
                .endDate(orderStage.getEndDate())
                .plannedDurationTime(orderStage.getPlannedDurationTime())
                .plannedFittersNumber(orderStage.getPlannedFittersNumber())
                .minimumImagesNumber(orderStage.getMinimumImagesNumber())
                .fitters(orderStage.getAssignedTo().stream().map(Fitter::getId).collect(Collectors.toList()))
                .foremanId(orderStage.getManagedBy() == null ? null : orderStage.getManagedBy().getId())
                .comments(orderStage.getComments().stream().map(Comment::getId).collect(Collectors.toList()))
                .toolReleases(orderStage.getToolReleases().stream().map(ToolRelease::getId).collect(Collectors.toList()))
                .elementReturnReleases(orderStage.getElementReturnReleases().stream().map(ElementReturnRelease::getId).collect(Collectors.toList()))
                .orderId(orderStage.getOrders() == null ? null : orderStage.getOrders().getId())
                .attachments(orderStage.getAttachments().stream().map(Attachment::getId).collect(Collectors.toList()))
                .notifications(orderStage.getNotifications().stream().map(Notification::getId).collect(Collectors.toList()))
                .listOfToolsPlannedNumber(orderStage.getListOfToolsPlannedNumber().stream().map(ToolsPlannedNumber::getId).collect(Collectors.toList()))
                .listOfElementsPlannedNumber(orderStage.getListOfElementsPlannedNumber().stream().map(ElementsPlannedNumber::getId).collect(Collectors.toList()))
                .demandAdHocs(orderStage.getDemandsAdHoc().stream().map(DemandAdHoc::getId).collect(Collectors.toList()))
                .build();
    }

    public OrderStage toEntity(OrderStageDto orderStageDto) {

        List<Fitter> fitterList = new ArrayList<>();
        orderStageDto.getFitters().forEach(fitterId -> fitterList.add(fitterRepository.getReferenceById(fitterId)));

        List<Comment> commentList = new ArrayList<>();
        orderStageDto.getComments().forEach(commentId -> commentList.add(commentRepository.getReferenceById(commentId)));

        List<ToolRelease> toolReleaseList = new ArrayList<>();
        orderStageDto.getToolReleases().forEach(toolReleaseId -> toolReleaseList.add(toolReleaseRepository.getReferenceById(toolReleaseId)));

        List<ElementReturnRelease> elementReturnReleaseList = new ArrayList<>();
        orderStageDto.getElementReturnReleases().forEach(elementReturnReleaseId -> elementReturnReleaseList.add(elementReturnReleaseRepository.getReferenceById(elementReturnReleaseId)));

        List<Attachment> attachmentList = new ArrayList<>();
        orderStageDto.getAttachments().forEach(attachmentId -> attachmentList.add(attachmentRepository.getReferenceById(attachmentId)));

        List<Notification> notificationList = new ArrayList<>();
        orderStageDto.getNotifications().forEach(notificationId -> notificationList.add(notificationRepository.getReferenceById(notificationId)));

        List<ToolsPlannedNumber> toolsPlannedNumbersList = new ArrayList<>();
        orderStageDto.getListOfElementsPlannedNumber().forEach(toolsPlannedNumbersId ->
                toolsPlannedNumbersList.add(toolsPlannedNumberRepository.getReferenceById(toolsPlannedNumbersId)));

        List<ElementsPlannedNumber> elementsPlannedNumberList = new ArrayList<>();
        orderStageDto.getListOfElementsPlannedNumber().forEach(elementsPlannedNumberId ->
                elementsPlannedNumberList.add(elementsPlannedNumberRepository.getReferenceById(elementsPlannedNumberId)));

        List<DemandAdHoc> demandAdHocList = new ArrayList<>();
        orderStageDto.getDemandAdHocs().forEach(demandAdHocId -> demandAdHocList.add(demandAdHocRepository.getReferenceById(demandAdHocId)));

        return OrderStage.builder()
                .id(orderStageDto.getId())
                .name(orderStageDto.getName())
                .status(orderStageDto.getStatus())
                .price(orderStageDto.getPrice())
                .plannedEndDate(orderStageDto.getPlannedEndDate())
                .startDate(orderStageDto.getStartDate())
                .endDate(orderStageDto.getEndDate())
                .plannedStartDate(orderStageDto.getPlannedStartDate())
                .plannedDurationTime(orderStageDto.getPlannedDurationTime())
                .plannedFittersNumber(orderStageDto.getPlannedFittersNumber())
                .minimumImagesNumber(orderStageDto.getMinimumImagesNumber())
                .assignedTo(fitterList)
                .managedBy(orderStageDto.getForemanId() == null ? null : foremanRepository.getReferenceById(orderStageDto.getForemanId()))
                .comments(commentList)
                .toolReleases(toolReleaseList)
                .elementReturnReleases(elementReturnReleaseList)
                .orders(orderStageDto.getOrderId() == null ? null : orderRepository.getReferenceById(orderStageDto.getOrderId()))
                .attachments(attachmentList)
                .notifications(notificationList)
                .listOfToolsPlannedNumber(toolsPlannedNumbersList)
                .listOfElementsPlannedNumber(elementsPlannedNumberList)
                .demandsAdHoc(demandAdHocList)
                .build();
    }
}
