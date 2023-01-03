package com.emontazysta.service;

import com.emontazysta.model.Attachment;

import java.util.List;

public interface AttachmentService {

    List<Attachment> getAll();
    Attachment getById(Long id);
    void add(Attachment attachment);
    void delete(Long id);
}
