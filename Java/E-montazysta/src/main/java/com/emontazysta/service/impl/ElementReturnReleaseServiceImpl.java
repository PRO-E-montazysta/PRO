package com.emontazysta.service.impl;

import com.emontazysta.model.ElementReturnRelease;
import com.emontazysta.repository.ElementReturnReleaseRepository;
import com.emontazysta.service.ElementReturnReleaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ElementReturnReleaseServiceImpl implements ElementReturnReleaseService {

    private final ElementReturnReleaseRepository repository;

    @Override
    public List<ElementReturnRelease> getAll() {
        return repository.findAll();
    }

    @Override
    public ElementReturnRelease getById(Long id) {
        return repository.findById(id)
                         .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void add(ElementReturnRelease elementReturnRelease) {
        repository.save(elementReturnRelease);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
