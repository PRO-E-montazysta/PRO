package com.emontazysta.mapper;

import com.emontazysta.model.Attachment;
import com.emontazysta.model.dto.AttachmentDto;
import com.emontazysta.repository.AppUserRepository;
import com.emontazysta.repository.CommentRepository;
import com.emontazysta.repository.ElementEventRepository;
import com.emontazysta.repository.ElementRepository;
import com.emontazysta.repository.FileSystemRepository;
import com.emontazysta.repository.OrderRepository;
import com.emontazysta.repository.OrderStageRepository;
import com.emontazysta.repository.ToolEventRepository;
import com.emontazysta.repository.ToolTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

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
    private final FileSystemRepository fileSystemRepository;

    public AttachmentDto toDto(Attachment attachment) {
        return AttachmentDto.builder()
                .id(attachment.getId())
                .name(attachment.getName())
                .uniqueName(attachment.getUniqueName())
                .path(attachment.getPath())
                .description(attachment.getDescription())
                .typeOfAttachment(attachment.getTypeOfAttachment())
                .createdAt(attachment.getCreatedAt())
                .fileName(attachment.getFileName())
                .toolTypeId(attachment.getToolType() == null ? null : attachment.getToolType().getId())
                .commentId(attachment.getComment() == null ? null : attachment.getComment().getId())
                .employeeId(attachment.getEmployee() == null ? null : attachment.getEmployee().getId())
                .toolEventId(attachment.getToolEvent() == null ? null : attachment.getToolEvent().getId())
                .orderId(attachment.getOrder() == null ? null : attachment.getOrder().getId())
                .elementId(attachment.getElement() == null ? null : attachment.getElement().getId())
                .orderStageId(attachment.getOrderStage() == null ? null : attachment.getOrderStage().getId())
                .elementEventId(attachment.getElementEvent() == null ? null : attachment.getElementEvent().getId())
                .deleted(attachment.isDeleted())
                .build();
    }

    public Attachment toEntity(AttachmentDto attachmentDto) {
        return Attachment.builder()
                .id(attachmentDto.getId())
                .name(attachmentDto.getName())
                .uniqueName(attachmentDto.getUniqueName())
                .description(attachmentDto.getDescription())
                .typeOfAttachment(attachmentDto.getTypeOfAttachment())
                .createdAt(attachmentDto.getCreatedAt())
                .toolType(attachmentDto.getToolTypeId() == null ? null : toolTypeRepository.findById(attachmentDto.getToolTypeId()).orElseThrow(EntityNotFoundException::new))
                .comment(attachmentDto.getCommentId() == null ? null : commentRepository.findById(attachmentDto.getCommentId()).orElseThrow(EntityNotFoundException::new))
                .employee(attachmentDto.getEmployeeId() == null ? null : appUserRepository.findById(attachmentDto.getEmployeeId()).orElseThrow(EntityNotFoundException::new))
                .toolEvent(attachmentDto.getToolEventId() == null ? null : toolEventRepository.findById(attachmentDto.getToolEventId()).orElseThrow(EntityNotFoundException::new))
                .order(attachmentDto.getOrderId() == null ? null : orderRepository.findById(attachmentDto.getOrderId()).orElseThrow(EntityNotFoundException::new))
                .element(attachmentDto.getElementId() == null ? null : elementRepository.findById(attachmentDto.getElementId()).orElseThrow(EntityNotFoundException::new))
                .orderStage(attachmentDto.getOrderStageId() == null ? null : orderStageRepository.findById(attachmentDto.getOrderStageId()).orElseThrow(EntityNotFoundException::new))
                .elementEvent(attachmentDto.getElementEventId() == null ? null : elementEventRepository.findById(attachmentDto.getElementEventId()).orElseThrow(EntityNotFoundException::new))
                .build();
    }

    public Attachment toEntity(AttachmentDto attachmentDto, MultipartFile file) throws IOException {

        Path path = fileSystemRepository.save(file.getBytes(), file.getOriginalFilename());

        return Attachment.builder()
                .id(attachmentDto.getId())
                .name(attachmentDto.getName())
                .uniqueName(path.getFileName().toString())
                .path(path.toAbsolutePath().toString())
                .description(attachmentDto.getDescription())
                .typeOfAttachment(attachmentDto.getTypeOfAttachment())
                .createdAt(attachmentDto.getCreatedAt())
                .fileName(file.getOriginalFilename())
                .toolType(attachmentDto.getToolTypeId() == null ? null : toolTypeRepository.findById(attachmentDto.getToolTypeId()).orElseThrow(EntityNotFoundException::new))
                .comment(attachmentDto.getCommentId() == null ? null : commentRepository.findById(attachmentDto.getCommentId()).orElseThrow(EntityNotFoundException::new))
                .employee(attachmentDto.getEmployeeId() == null ? null : appUserRepository.findById(attachmentDto.getEmployeeId()).orElseThrow(EntityNotFoundException::new))
                .toolEvent(attachmentDto.getToolEventId() == null ? null : toolEventRepository.findById(attachmentDto.getToolEventId()).orElseThrow(EntityNotFoundException::new))
                .order(attachmentDto.getOrderId() == null ? null : orderRepository.findById(attachmentDto.getOrderId()).orElseThrow(EntityNotFoundException::new))
                .element(attachmentDto.getElementId() == null ? null : elementRepository.findById(attachmentDto.getElementId()).orElseThrow(EntityNotFoundException::new))
                .orderStage(attachmentDto.getOrderStageId() == null ? null : orderStageRepository.findById(attachmentDto.getOrderStageId()).orElseThrow(EntityNotFoundException::new))
                .elementEvent(attachmentDto.getElementEventId() == null ? null : elementEventRepository.findById(attachmentDto.getElementEventId()).orElseThrow(EntityNotFoundException::new))
                .build();
    }
}
