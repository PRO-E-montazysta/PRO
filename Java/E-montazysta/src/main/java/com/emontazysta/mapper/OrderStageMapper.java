package com.emontazysta.mapper;

import com.emontazysta.model.*;
import com.emontazysta.model.dto.OrderStageDto;
import com.emontazysta.model.dto.OrderStageWithToolsAndElementsDto;
import com.emontazysta.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderStageMapper {

    private final FitterRepository fitterRepository;
    private final CommentRepository commentRepository;
    private final ToolReleaseRepository toolReleaseRepository;
    private final ElementReturnReleaseRepository elementReturnReleaseRepository;
    private final OrderRepository orderRepository;
    private final AttachmentRepository attachmentRepository;
    private final NotificationRepository notificationRepository;
    private final DemandAdHocRepository demandAdHocRepository;
    private final ToolsPlannedNumberRepository toolsPlannedNumberRepository;
    private final ToolsPlannedNumberMapper toolsPlannedNumberMapper;
    private final ElementsPlannedNumberRepository elementsPlannedNumberRepository;
    private final ElementsPlannedNumberMapper elementsPlannedNumberMapper;
    private final ToolReleaseMapper toolReleaseMapper;
    private final ElementReturnReleaseMapper elementReturnReleaseMapper;

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
                .plannedDurationTime(ChronoUnit.HOURS.between(orderStage.getPlannedStartDate(),orderStage.getPlannedEndDate()))
                .plannedFittersNumber(orderStage.getPlannedFittersNumber())
                .minimumImagesNumber(orderStage.getMinimumImagesNumber())
                .fitters(orderStage.getAssignedTo().stream().map(Fitter::getId).collect(Collectors.toList()))
                .comments(orderStage.getComments().stream().map(Comment::getId).collect(Collectors.toList()))
                .toolReleases(orderStage.getToolReleases().stream().map(ToolRelease::getId).collect(Collectors.toList()))
                .simpleToolReleases(orderStage.getToolReleases().stream().map(toolReleaseMapper::toDto).collect(Collectors.toList()))
                .elementReturnReleases(orderStage.getElementReturnReleases().stream().map(ElementReturnRelease::getId).collect(Collectors.toList()))
                .simpleElementReturnReleases(orderStage.getElementReturnReleases().stream().map(elementReturnReleaseMapper::toDto).collect(Collectors.toList()))
                .orderId(orderStage.getOrders() == null ? null : orderStage.getOrders().getId())
                .orderName(orderStage.getOrders() == null ? null : orderStage.getOrders().getName())
		        .foremanId(orderStage.getOrders() == null ? null : orderStage.getOrders().getAssignedTo() == null ? null : orderStage.getOrders().getAssignedTo().getId())
                .attachments(orderStage.getAttachments().stream()
                        .map(Attachment::getId)
                        .collect(Collectors.toList()))
                .notifications(orderStage.getNotifications().stream()
                        .map(Notification::getId)
                        .collect(Collectors.toList()))
                .listOfToolsPlannedNumber(orderStage.getListOfToolsPlannedNumber().stream()
                        .map(ToolsPlannedNumber::getId)
                        .collect(Collectors.toList()))
                .listOfElementsPlannedNumber(orderStage.getListOfElementsPlannedNumber().stream()
                        .map(ElementsPlannedNumber::getId)
                        .collect(Collectors.toList()))
                .demandAdHocs(orderStage.getDemandsAdHoc().stream()
                        .map(DemandAdHoc::getId)
                        .collect(Collectors.toList()))
                .deleted(orderStage.isDeleted())
                .build();
    }

    public OrderStage toEntity(OrderStageDto orderStageDto) {

        List<Fitter> fitterList = new ArrayList<>();
        orderStageDto.getFitters().forEach(fitterId -> fitterList.add(fitterRepository.findById(fitterId).orElseThrow(EntityNotFoundException::new)));

        List<Comment> commentList = new ArrayList<>();
        orderStageDto.getComments().forEach(commentId -> commentList.add(commentRepository.findById(commentId).orElseThrow(EntityNotFoundException::new)));

        List<ToolRelease> toolReleaseList = new ArrayList<>();
        orderStageDto.getToolReleases().forEach(toolReleaseId -> toolReleaseList.add(toolReleaseRepository.findById(toolReleaseId).orElseThrow(EntityNotFoundException::new)));

        List<ElementReturnRelease> elementReturnReleaseList = new ArrayList<>();
        orderStageDto.getElementReturnReleases().forEach(elementReturnReleaseId -> elementReturnReleaseList.add(elementReturnReleaseRepository.findById(elementReturnReleaseId).orElseThrow(EntityNotFoundException::new)));

        List<Attachment> attachmentList = new ArrayList<>();
        orderStageDto.getAttachments().forEach(attachmentId -> attachmentList.add(attachmentRepository.findById(attachmentId).orElseThrow(EntityNotFoundException::new)));

        List<Notification> notificationList = new ArrayList<>();
        orderStageDto.getNotifications().forEach(notificationId -> notificationList.add(notificationRepository.findById(notificationId).orElseThrow(EntityNotFoundException::new)));

        List<ToolsPlannedNumber> toolTypeList = new ArrayList<>();
        orderStageDto.getListOfToolsPlannedNumber().forEach(toolTypeId -> toolTypeList.add(toolsPlannedNumberRepository.findById(toolTypeId).orElseThrow(EntityNotFoundException::new)));

        List<ElementsPlannedNumber> elementList = new ArrayList<>();
        orderStageDto.getListOfElementsPlannedNumber().forEach(elementId -> elementList.add(elementsPlannedNumberRepository.findById(elementId).orElseThrow(EntityNotFoundException::new)));

        List<DemandAdHoc> demandAdHocList = new ArrayList<>();
        orderStageDto.getDemandAdHocs().forEach(demandAdHocId -> demandAdHocList.add(demandAdHocRepository.findById(demandAdHocId).orElseThrow(EntityNotFoundException::new)));

        return OrderStage.builder()
                .id(orderStageDto.getId())
                .name(orderStageDto.getName())
                .status(orderStageDto.getStatus())
                .price(orderStageDto.getPrice())
                .plannedEndDate(orderStageDto.getPlannedEndDate())
                .startDate(orderStageDto.getStartDate())
                .endDate(orderStageDto.getEndDate())
                .plannedStartDate(orderStageDto.getPlannedStartDate())
                .plannedDurationTime(ChronoUnit.HOURS.between(orderStageDto.getPlannedStartDate(),orderStageDto.getPlannedEndDate()))
                .plannedFittersNumber(orderStageDto.getPlannedFittersNumber())
                .minimumImagesNumber(orderStageDto.getMinimumImagesNumber())
                .assignedTo(fitterList)
                .comments(commentList)
                .toolReleases(toolReleaseList)
                .elementReturnReleases(elementReturnReleaseList)
                .orders(orderStageDto.getOrderId() == null ? null : orderRepository.findById(orderStageDto.getOrderId()).orElseThrow(EntityNotFoundException::new))
                .attachments(attachmentList)
                .notifications(notificationList)
                .listOfToolsPlannedNumber(toolTypeList)
                .listOfElementsPlannedNumber(elementList)
                .demandsAdHoc(demandAdHocList)
                .build();
    }

    public OrderStage toEntity(OrderStageWithToolsAndElementsDto orderStageToolsElementsDto) {

        List<Fitter> fitterList = new ArrayList<>();
        orderStageToolsElementsDto.getFitters().forEach(fitterId -> fitterList.add(fitterRepository.findById(fitterId).orElseThrow(EntityNotFoundException::new)));

        List<Comment> commentList = new ArrayList<>();
        orderStageToolsElementsDto.getComments().forEach(commentId -> commentList.add(commentRepository.findById(commentId).orElseThrow(EntityNotFoundException::new)));

        List<ToolRelease> toolReleaseList = new ArrayList<>();
        orderStageToolsElementsDto.getToolReleases().forEach(toolReleaseId -> toolReleaseList.add(toolReleaseRepository.findById(toolReleaseId).orElseThrow(EntityNotFoundException::new)));

        List<ElementReturnRelease> elementReturnReleaseList = new ArrayList<>();
        orderStageToolsElementsDto.getElementReturnReleases().forEach(elementReturnReleaseId -> elementReturnReleaseList.add(elementReturnReleaseRepository.findById(elementReturnReleaseId).orElseThrow(EntityNotFoundException::new)));

        List<Attachment> attachmentList = new ArrayList<>();
        orderStageToolsElementsDto.getAttachments().forEach(attachmentId -> attachmentList.add(attachmentRepository.findById(attachmentId).orElseThrow(EntityNotFoundException::new)));

        List<Notification> notificationList = new ArrayList<>();
        orderStageToolsElementsDto.getNotifications().forEach(notificationId -> notificationList.add(notificationRepository.findById(notificationId).orElseThrow(EntityNotFoundException::new)));

        List<ToolsPlannedNumber> toolTypeList = new ArrayList<>();
        orderStageToolsElementsDto.getListOfToolsPlannedNumber().forEach(toolsPlannedNumberDto -> toolTypeList.add(toolsPlannedNumberMapper.toEntity(toolsPlannedNumberDto)));

        List<ElementsPlannedNumber> elementList = new ArrayList<>();
        orderStageToolsElementsDto.getListOfElementsPlannedNumber().forEach(elementsPlannedNumberDto -> elementList.add(elementsPlannedNumberMapper.toEntity(elementsPlannedNumberDto)));

        List<DemandAdHoc> demandAdHocList = new ArrayList<>();
        orderStageToolsElementsDto.getDemandAdHocs().forEach(demandAdHocId -> demandAdHocList.add(demandAdHocRepository.findById(demandAdHocId).orElseThrow(EntityNotFoundException::new)));

        return OrderStage.builder()
                .id(orderStageToolsElementsDto.getId())
                .name(orderStageToolsElementsDto.getName())
                .status(orderStageToolsElementsDto.getStatus())
                .price(orderStageToolsElementsDto.getPrice())
                .plannedEndDate(orderStageToolsElementsDto.getPlannedEndDate())
                .startDate(orderStageToolsElementsDto.getStartDate())
                .endDate(orderStageToolsElementsDto.getEndDate())
                .plannedStartDate(orderStageToolsElementsDto.getPlannedStartDate())
                .plannedDurationTime(ChronoUnit.HOURS.between(orderStageToolsElementsDto.getPlannedStartDate(),orderStageToolsElementsDto.getPlannedEndDate()))
                .plannedFittersNumber(orderStageToolsElementsDto.getPlannedFittersNumber())
                .minimumImagesNumber(orderStageToolsElementsDto.getMinimumImagesNumber())
                .assignedTo(fitterList)
                .comments(commentList)
                .toolReleases(toolReleaseList)
                .elementReturnReleases(elementReturnReleaseList)
                .orders(orderStageToolsElementsDto.getOrderId() == null ? null : orderRepository.findById(orderStageToolsElementsDto.getOrderId()).orElseThrow(EntityNotFoundException::new))
                .attachments(attachmentList)
                .notifications(notificationList)
                .listOfToolsPlannedNumber(toolTypeList)
                .listOfElementsPlannedNumber(elementList)
                .demandsAdHoc(demandAdHocList)
                .build();
    }
}
