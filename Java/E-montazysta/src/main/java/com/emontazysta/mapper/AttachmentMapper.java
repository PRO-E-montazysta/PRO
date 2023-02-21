package com.emontazysta.mapper;

import com.emontazysta.model.Attachment;
import com.emontazysta.model.dto.AttachmentDto;
import com.emontazysta.repository.AppUserRepository;
import com.emontazysta.repository.CommentRepository;
import com.emontazysta.repository.ElementEventRepository;
import com.emontazysta.repository.ElementRepository;
import com.emontazysta.repository.OrderRepository;
import com.emontazysta.repository.OrderStageRepository;
import com.emontazysta.repository.ToolEventRepository;
import com.emontazysta.repository.ToolTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AttachmentMapper {

    private final ToolTypeRepository toolTypeRepository;
    private final CommentRepository commentRepository;
    private final AppUserRepository appUserRepository;
    private final ToolEventRepository toolEventRepository;
    private final OrderRepository orderRepository;
    private final ElementRepository elementRepository;
    private final OrderStageRepository orderStageRepository;
    private final ElementEventRepository elementEventRepository;

    public AttachmentDto toDto(Attachment attachment) {
        return AttachmentDto.builder()
                .id(attachment.getId())
                .name(attachment.getName())
                .url(attachment.getUrl())
                .description(attachment.getDescription())
                .typeOfAttachment(attachment.getTypeOfAttachment())
                .createdAt(attachment.getCreatedAt())
                .toolTypeId(attachment.getToolType() == null ? null : attachment.getToolType().getId())
                .commentId(attachment.getComment() == null ? null : attachment.getComment().getId())
                .employeeId(attachment.getEmployee() == null ? null : attachment.getEmployee().getId())
                .toolEventId(attachment.getToolEvent() == null ? null : attachment.getToolEvent().getId())
                .orderId(attachment.getOrder() == null ? null : attachment.getOrder().getId())
                .elementId(attachment.getElement() == null ? null : attachment.getElement().getId())
                .orderStageId(attachment.getOrderStage() == null ? null :attachment.getOrderStage().getId())
                .elementEventId(attachment.getElementEvent() == null ? null : attachment.getElementEvent().getId())
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
                .toolType(attachmentDto.getToolTypeId() == null ? null : toolTypeRepository.getReferenceById(attachmentDto.getToolTypeId()))
                .comment(attachmentDto.getCommentId() == null ? null : commentRepository.getReferenceById(attachmentDto.getCommentId()))
                .employee(attachmentDto.getEmployeeId() == null ? null : appUserRepository.getReferenceById(attachmentDto.getEmployeeId()))
                .toolEvent(attachmentDto.getToolEventId() == null ? null : toolEventRepository.getReferenceById(attachmentDto.getToolEventId()))
                .order(attachmentDto.getOrderId() == null ? null : orderRepository.getReferenceById(attachmentDto.getOrderId()))
                .element(attachmentDto.getElementId() == null ? null : elementRepository.getReferenceById(attachmentDto.getElementId()))
                .orderStage(attachmentDto.getOrderStageId() == null ? null : orderStageRepository.getReferenceById(attachmentDto.getOrderStageId()))
                .elementEvent(attachmentDto.getElementEventId() == null ? null : elementEventRepository.getReferenceById(attachmentDto.getElementEventId()))
                .build();
    }
}
