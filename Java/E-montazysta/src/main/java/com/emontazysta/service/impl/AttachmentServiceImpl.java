package com.emontazysta.service.impl;

import com.emontazysta.mapper.AttachmentMapper;
import com.emontazysta.model.Attachment;
import com.emontazysta.model.dto.AttachmentDto;
import com.emontazysta.repository.AttachmentRepository;
import com.emontazysta.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository repository;
    private final AttachmentMapper attachmentMapper;

    @Override
    public List<AttachmentDto> getAll() {
        return repository.findAllByDeletedIsFalse().stream()
                .map(attachmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AttachmentDto getById(Long id) {
        Attachment attachment = repository.findByIdAndDeletedIsFalse(id).orElseThrow(EntityNotFoundException::new);
        return attachmentMapper.toDto(attachment);
    }

    @Override
    public AttachmentDto add(AttachmentDto attachmentDto) {
        Attachment attachment = attachmentMapper.toEntity(attachmentDto);
        attachment.setCreatedAt(LocalDateTime.now());
        return attachmentMapper.toDto(repository.save(attachment));
    }

    @Override
    public void delete(Long id) {

        Attachment attachment = repository.findByIdAndDeletedIsFalse(id).orElseThrow(EntityNotFoundException::new);
        attachment.setDeleted(true);
        attachment.setToolType(null);
        attachment.setComment(null);
        attachment.setEmployee(null);
        attachment.setToolEvent(null);
        attachment.setOrder(null);
        attachment.setElement(null);
        attachment.setOrderStage(null);
        attachment.setElementEvent(null);
        repository.save(attachment);
    }

    @Override
    public AttachmentDto update(Long id, AttachmentDto attachmentDto) {
        Attachment updatedAttachment = attachmentMapper.toEntity(attachmentDto);
        Attachment attachment = repository.findByIdAndDeletedIsFalse(id).orElseThrow(EntityNotFoundException::new);
        attachment.setName(updatedAttachment.getName());
        attachment.setUrl(updatedAttachment.getUrl());
        attachment.setDescription(updatedAttachment.getDescription());
        attachment.setToolType(updatedAttachment.getToolType());
        attachment.setComment(updatedAttachment.getComment());
        attachment.setEmployee(updatedAttachment.getEmployee());
        attachment.setToolEvent(updatedAttachment.getToolEvent());
        attachment.setOrder(updatedAttachment.getOrder());
        attachment.setElement(updatedAttachment.getElement());
        attachment.setOrderStage(updatedAttachment.getOrderStage());
        attachment.setElementEvent(updatedAttachment.getElementEvent());
        return attachmentMapper.toDto(repository.save(attachment));
    }
}
