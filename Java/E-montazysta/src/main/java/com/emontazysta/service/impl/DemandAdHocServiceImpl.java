package com.emontazysta.service.impl;

import com.emontazysta.model.DemandAdHoc;
import com.emontazysta.repository.DemandAdHocRepository;
import com.emontazysta.service.DemandAdHocService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DemandAdHocServiceImpl implements DemandAdHocService {

    private final DemandAdHocRepository repository;

    @Override
    public List<DemandAdHoc> getAll() {
        return repository.findAll();
    }

    @Override
    public DemandAdHoc getById(Long id) {
        return repository.findById(id)
                         .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void add(DemandAdHoc demandAdHoc) {
        repository.save(demandAdHoc);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
