package com.emontazysta.service;

import com.emontazysta.model.dto.AttachmentDto;

import java.util.List;

public interface AttachmentService {

    List<AttachmentDto> getAll();
    AttachmentDto getById(Long id);
    AttachmentDto add(AttachmentDto attachment);
    void delete(Long id);
    AttachmentDto update(Long id, AttachmentDto attachment);
}
