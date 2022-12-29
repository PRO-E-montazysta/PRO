package com.emontazysta.service.impl;

import com.emontazysta.model.Foreman;
import com.emontazysta.repository.ForemanRepository;
import com.emontazysta.service.ForemanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ForemanServiceImpl implements ForemanService {

    private final ForemanRepository repository;

    @Override
    public List<Foreman> getAll() {
        return repository.findAll();
    }

    @Override
    public Foreman getById(Long id) {
        return repository.findById(id)
                         .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void add(Foreman foreman) {
        repository.save(foreman);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
