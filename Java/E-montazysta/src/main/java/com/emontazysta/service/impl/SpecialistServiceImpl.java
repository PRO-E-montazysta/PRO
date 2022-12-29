package com.emontazysta.service.impl;

import com.emontazysta.model.Specialist;
import com.emontazysta.repository.SpecialistRepository;
import com.emontazysta.service.SpecialistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialistServiceImpl implements SpecialistService {

    private final SpecialistRepository repository;


    @Override
    public List<Specialist> getAll() {
        return repository.findAll();
    }

    @Override
    public Specialist getById(Long id) {
        return repository.findById(id)
                         .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void add(Specialist specialist) {
        repository.save(specialist);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
