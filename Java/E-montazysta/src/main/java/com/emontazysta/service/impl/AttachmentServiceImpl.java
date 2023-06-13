package com.emontazysta.service.impl;

import com.emontazysta.mapper.AttachmentMapper;
import com.emontazysta.model.Attachment;
import com.emontazysta.model.dto.AttachmentDto;
import com.emontazysta.repository.AttachmentRepository;
import com.emontazysta.repository.FileSystemRepository;
import com.emontazysta.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository repository;
    private final AttachmentMapper attachmentMapper;
    private final FileSystemRepository fileSystemRepository;

    @Override
    public List<AttachmentDto> getAll() {
        return repository.findAll().stream()
                .map(attachmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AttachmentDto getById(Long id) {
        Attachment attachment = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return attachmentMapper.toDto(attachment);
    }

    @Override
    public AttachmentDto getByUniqueName(String uniqueName) {
        Attachment attachment = repository.findByUniqueNameAndDeletedIsFalse(uniqueName).orElseThrow(EntityNotFoundException::new);
        return attachmentMapper.toDto(attachment);
    }

    @Override
    public AttachmentDto add(AttachmentDto attachmentDto, MultipartFile file) {
        try {
            Attachment attachment = attachmentMapper.toEntity(attachmentDto, file);
            attachment.setCreatedAt(LocalDateTime.now());
            return attachmentMapper.toDto(repository.save(attachment));
        } catch (NoSuchElementException e) {
            log.error("Error during saving attachment - filename does not contain correct extension", e);
            return null;
        } catch (IOException e) {
            log.error("Error during saving attachment", e);
            return null;
        }
    }

    @Override
    public void delete(Long id) {

        repository.deleteById(id);
    }

    @Override
    public AttachmentDto update(Long id, AttachmentDto attachmentDto, MultipartFile file) {

        Attachment updatedAttachment;
        if (file == null) {
            updatedAttachment = attachmentMapper.toEntity(attachmentDto);
        } else {
            try {
                updatedAttachment = attachmentMapper.toEntity(attachmentDto, file);
            } catch (IOException e) {
                log.error("Error during saving attachment", e);
                return null;
            }
        }

        Attachment attachment = repository.findById(id).orElseThrow(EntityNotFoundException::new);

        if (file != null) {
            try {
                fileSystemRepository.delete(attachment.getUniqueName());
            } catch (IOException e) {
                log.error("Error during deleting attachment", e);
                return null;
            }
            attachment.setFileName(updatedAttachment.getFileName());
        }

        attachment.setName(updatedAttachment.getName());
        attachment.setUniqueName(updatedAttachment.getUniqueName());
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
