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

import javax.persistence.EntityNotFoundException;
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
                .fitters(orderStage.getAssignedTo().stream()
                        .filter(fitter -> !fitter.isDeleted())
                        .map(Fitter::getId)
                        .collect(Collectors.toList()))
                .foremanId(orderStage.getManagedBy() == null ? null : orderStage.getManagedBy().isDeleted() ? null : orderStage.getManagedBy().getId())
                .comments(orderStage.getComments().stream()
                        .filter(comment -> !comment.isDeleted())
                        .map(Comment::getId)
                        .collect(Collectors.toList()))
                .toolReleases(orderStage.getToolReleases().stream()
                        .filter(toolRelease -> !toolRelease.isDeleted())
                        .map(ToolRelease::getId)
                        .collect(Collectors.toList()))
                .elementReturnReleases(orderStage.getElementReturnReleases().stream()
                        .filter(elementReturnRelease -> !elementReturnRelease.isDeleted())
                        .map(ElementReturnRelease::getId)
                        .collect(Collectors.toList()))
                .orderId(orderStage.getOrders() == null ? null : orderStage.getOrders().isDeleted() ? null : orderStage.getOrders().getId())
                .attachments(orderStage.getAttachments().stream()
                        .filter(attachment -> !attachment.isDeleted())
                        .map(Attachment::getId)
                        .collect(Collectors.toList()))
                .notifications(orderStage.getNotifications().stream()
                        .filter(notification -> !notification.isDeleted())
                        .map(Notification::getId)
                        .collect(Collectors.toList()))
                .tools(orderStage.getTools().stream()
                        .filter(tool -> !tool.isDeleted())
                        .map(ToolType::getId)
                        .collect(Collectors.toList()))
                .elements(orderStage.getElements().stream()
                        .filter(element -> !element.isDeleted())
                        .map(Element::getId)
                        .collect(Collectors.toList()))
                .demandAdHocs(orderStage.getDemandsAdHoc().stream()
                        .filter(demandAdHoc -> !demandAdHoc.isDeleted())
                        .map(DemandAdHoc::getId)
                        .collect(Collectors.toList()))
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

        List<ToolType> toolTypeList = new ArrayList<>();
        orderStageDto.getTools().forEach(toolTypeId -> toolTypeList.add(toolTypeRepository.findById(toolTypeId).orElseThrow(EntityNotFoundException::new)));

        List<Element> elementList = new ArrayList<>();
        orderStageDto.getElements().forEach(elementId -> elementList.add(elementRepository.findById(elementId).orElseThrow(EntityNotFoundException::new)));

        List<DemandAdHoc> demandAdHocList = new ArrayList<>();
        orderStageDto.getDemandAdHocs().forEach(demandAdHocId -> demandAdHocList.add(demandAdHocRepository.findById(demandAdHocId).orElseThrow(EntityNotFoundException::new)));

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
                .managedBy(orderStageDto.getForemanId() == null ? null : foremanRepository.findById(orderStageDto.getForemanId()).orElseThrow(EntityNotFoundException::new))
                .comments(commentList)
                .toolReleases(toolReleaseList)
                .elementReturnReleases(elementReturnReleaseList)
                .orders(orderStageDto.getOrderId() == null ? null : orderRepository.findById(orderStageDto.getOrderId()).orElseThrow(EntityNotFoundException::new))
                .attachments(attachmentList)
                .notifications(notificationList)
                .tools(toolTypeList)
                .elements(elementList)
                .demandsAdHoc(demandAdHocList)
                .build();
    }
}
