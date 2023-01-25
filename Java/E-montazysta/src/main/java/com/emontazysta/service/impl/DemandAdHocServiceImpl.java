package com.emontazysta.service.impl;

import com.emontazysta.model.DemandAdHoc;
import com.emontazysta.repository.DemandAdHocRepository;
import com.emontazysta.service.DemandAdHocService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
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
        demandAdHoc.setCreationTime(LocalDateTime.now());
        repository.save(demandAdHoc);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public DemandAdHoc update(Long id, DemandAdHoc demandAdHoc) {

        DemandAdHoc demandAdHocDb = getById(id);
        demandAdHocDb.setDescription(demandAdHoc.getDescription());
        demandAdHocDb.setComments(demandAdHoc.getComments());
        demandAdHocDb.setReadByWarehousemanTime(demandAdHoc.getReadByWarehousemanTime());
        demandAdHocDb.setRealisationTime(demandAdHoc.getRealisationTime());
        demandAdHocDb.setWarehousemanComment(demandAdHoc.getWarehousemanComment());
        demandAdHocDb.setSpecialistComment(demandAdHoc.getSpecialistComment());

        return demandAdHocDb;
    }
}
