package com.emontazysta.service;

import com.emontazysta.model.dto.AttachmentDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AttachmentService {

    List<AttachmentDto> getAll();
    AttachmentDto getById(Long id);
    AttachmentDto add(AttachmentDto attachment, MultipartFile file);
    void delete(Long id);
    AttachmentDto update(Long id, AttachmentDto attachment, MultipartFile file);
}
