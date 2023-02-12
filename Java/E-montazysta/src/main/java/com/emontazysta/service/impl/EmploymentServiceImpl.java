package com.emontazysta.service.impl;

import com.emontazysta.model.Employment;
import com.emontazysta.repository.EmploymentRepository;
import com.emontazysta.service.EmploymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmploymentServiceImpl implements EmploymentService {

    private final EmploymentRepository repository;

    @Override
    public List<Employment> getAll() {
        return repository.findAll();
    }

    @Override
    public Employment getById(Long id) {
        return repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void add(Employment employment) {
        repository.save(employment);
    }
}
