package com.emontazysta.service.impl;

import com.emontazysta.model.Attachment;
import com.emontazysta.repository.AttachmentRepository;
import com.emontazysta.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository repository;

    @Override
    public List<Attachment> getAll() {
        return repository.findAll();
    }

    @Override
    public Attachment getById(Long id) {
        return repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void add(Attachment attachment) {
        attachment.setCreatedAt(new Date());
        repository.save(attachment);
    }

    @Override
    public void delete(Long id) {
    repository.deleteById(id);
    }
}
