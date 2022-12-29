package com.emontazysta.service.impl;

import com.emontazysta.model.Fitter;
import com.emontazysta.repository.FitterRepository;
import com.emontazysta.service.FitterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FitterServiceImpl implements FitterService {

    private final FitterRepository repository;

    @Override
    public List<Fitter> getAll() {
        return repository.findAll();
    }


    @Override
    public Fitter getById(Long id) {
        return repository.findById(id)
                         .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void add(Fitter fitter) {
        repository.save(fitter);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
