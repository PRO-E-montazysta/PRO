package com.emontazysta.mapper;

import com.emontazysta.model.Attachment;
import com.emontazysta.model.Comment;
import com.emontazysta.model.DemandAdHoc;
import com.emontazysta.model.Element;
import com.emontazysta.model.ElementReturnRelease;
import com.emontazysta.model.Fitter;
import com.emontazysta.model.Notification;
import com.emontazysta.model.OrderStage;
import com.emontazysta.model.ToolRelease;
import com.emontazysta.model.ToolType;
import com.emontazysta.model.dto.OrderStageDto;
import com.emontazysta.repository.AttachmentRepository;
import com.emontazysta.repository.CommentRepository;
import com.emontazysta.repository.DemandAdHocRepository;
import com.emontazysta.repository.ElementRepository;
import com.emontazysta.repository.ElementReturnReleaseRepository;
import com.emontazysta.repository.FitterRepository;
import com.emontazysta.repository.ForemanRepository;
import com.emontazysta.repository.NotificationRepository;
import com.emontazysta.repository.OrderRepository;
import com.emontazysta.repository.ToolReleaseRepository;
import com.emontazysta.repository.ToolTypeRepository;
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
    private final ToolTypeRepository toolTypeRepository;
    private final ElementRepository elementRepository;
    private final DemandAdHocRepository demandAdHocRepository;

    public OrderStageDto toDto(OrderStage orderStage) {
        return OrderStageDto.builder()
                .id(orderStage.getId())
                .name(orderStage.getName())
                .status(orderStage.getStatus())
                .price(orderStage.getPrice())
                .order(orderStage.getOrder())
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
                .tools(orderStage.getTools().stream().map(ToolType::getId).collect(Collectors.toList()))
                .elements(orderStage.getElements().stream().map(Element::getId).collect(Collectors.toList()))
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

        List<ToolType> toolTypeList = new ArrayList<>();
        orderStageDto.getTools().forEach(toolTypeId -> toolTypeList.add(toolTypeRepository.getReferenceById(toolTypeId)));

        List<Element> elementList = new ArrayList<>();
        orderStageDto.getElements().forEach(elementId -> elementList.add(elementRepository.getReferenceById(elementId)));

        List<DemandAdHoc> demandAdHocList = new ArrayList<>();
        orderStageDto.getDemandAdHocs().forEach(demandAdHocId -> demandAdHocList.add(demandAdHocRepository.getReferenceById(demandAdHocId)));

        return OrderStage.builder()
                .id(orderStageDto.getId())
                .name(orderStageDto.getName())
                .status(orderStageDto.getStatus())
                .price(orderStageDto.getPrice())
                .order(orderStageDto.getOrder())
                .plannedEndDate(orderStageDto.getPlannedEndDate())
                .startDate(orderStageDto.getStartDate())
                .endDate(orderStageDto.getEndDate())
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
                .tools(toolTypeList)
                .elements(elementList)
                .demandsAdHoc(demandAdHocList)
                .build();
    }
}
