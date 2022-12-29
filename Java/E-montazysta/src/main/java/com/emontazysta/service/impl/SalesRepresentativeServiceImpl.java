package com.emontazysta.service.impl;

import com.emontazysta.model.SalesRepresentative;
import com.emontazysta.repository.SalesRepresentativeRepository;
import com.emontazysta.service.SalesRepresentativeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesRepresentativeServiceImpl implements SalesRepresentativeService {

    private final SalesRepresentativeRepository repository;

    @Override
    public List<SalesRepresentative> getAll() {
        return repository.findAll();
    }

    @Override
    public SalesRepresentative getById(Long id) {
        return repository.findById(id)
                         .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void add(SalesRepresentative salesRepresentative) {
        repository.save(salesRepresentative);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
