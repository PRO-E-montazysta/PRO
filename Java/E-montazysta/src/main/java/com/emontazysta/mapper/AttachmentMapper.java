package com.emontazysta.mapper;

import com.emontazysta.model.Attachment;
import com.emontazysta.model.dto.AttachmentDto;
import com.emontazysta.service.AppUserService;
import com.emontazysta.service.CommentService;
import com.emontazysta.service.ElementEventService;
import com.emontazysta.service.ElementService;
import com.emontazysta.service.OrderStageService;
import com.emontazysta.service.OrdersService;
import com.emontazysta.service.ToolEventService;
import com.emontazysta.service.ToolTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AttachmentMapper {

    private final ToolTypeService toolTypeService;
    private final CommentService commentService;
    private final AppUserService appUserService;
    private final ToolEventService toolEventService;
    private final OrdersService orderService;
    private final ElementService elementService;
    private final OrderStageService orderStageService;
    private final ElementEventService elementEventService;

    public AttachmentDto attachmentDto(Attachment attachment) {
        return AttachmentDto.builder()
                .id(attachment.getId())
                .name(attachment.getName())
                .url(attachment.getUrl())
                .description(attachment.getDescription())
                .typeOfAttachment(attachment.getTypeOfAttachment())
                .createdAt(attachment.getCreatedAt())
                .toolTypeId(attachment.getToolType().getId())
                .commentId(attachment.getComment().getId())
                .employeeId(attachment.getEmployee().getId())
                .toolEventId(attachment.getToolEvent().getId())
                .orderId(attachment.getOrder().getId())
                .elementId(attachment.getElement().getId())
                .orderStageId(attachment.getOrderStage().getId())
                .elementEventId(attachment.getElementEvent().getId())
                .build();
    }

    public Attachment toEntity(AttachmentDto attachmentDto) {
        return Attachment.builder()
                .id(attachmentDto.getId())
                .name(attachmentDto.getName())
                .url(attachmentDto.getUrl())
                .description(attachmentDto.getDescription())
                .typeOfAttachment(attachmentDto.getTypeOfAttachment())
                .createdAt(attachmentDto.getCreatedAt())
                .toolType(toolTypeService.getById(attachmentDto.getToolTypeId()))
                .comment(commentService.getById(attachmentDto.getCommentId()))
                .employee(appUserService.getById(attachmentDto.getEmployeeId()))
                .toolEvent(toolEventService.getById(attachmentDto.getToolEventId()))
                .order(orderService.getById(attachmentDto.getOrderId()))
                .element(elementService.getById(attachmentDto.getElementId()))
                .orderStage(orderStageService.getById(attachmentDto.getOrderStageId()))
                .elementEvent(elementEventService.getById(attachmentDto.getElementEventId()))
                .build();
    }
}
