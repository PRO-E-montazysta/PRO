package com.emontazysta.service.impl;

import com.emontazysta.mapper.AttachmentMapper;
import com.emontazysta.model.Attachment;
import com.emontazysta.model.dto.AttachmentDto;
import com.emontazysta.repository.AttachmentRepository;
import com.emontazysta.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository repository;
    private final AttachmentMapper attachmentMapper;

    @Override
    public List<AttachmentDto> getAll() {
        return repository.findAll().stream()
                .map(attachmentMapper::attachmentDto)
                .collect(Collectors.toList());
    }

    @Override
    public AttachmentDto getById(Long id) {
        Attachment attachment = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return attachmentMapper.attachmentDto(attachment);
    }

    @Override
    public AttachmentDto add(AttachmentDto attachmentDto) {
        Attachment attachment = attachmentMapper.toEntity(attachmentDto);
        attachment.setCreatedAt(new Date());
        return attachmentMapper.attachmentDto(repository.save(attachment));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
