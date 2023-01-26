package com.emontazysta.service.impl;

import com.emontazysta.model.ToolRelease;
import com.emontazysta.repository.ToolReleaseRepository;
import com.emontazysta.service.ToolReleaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ToolReleaseServiceImpl implements ToolReleaseService {

    private final ToolReleaseRepository repository;

    @Override
    public List<ToolRelease> getAll() {
        return repository.findAll();
    }

    @Override
    public ToolRelease getById(Long id) {
        return repository.findById(id)
                         .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void add(ToolRelease toolRelease) {
        repository.save(toolRelease);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public ToolRelease update(Long id, ToolRelease toolRelease) {
        ToolRelease toolReleaseDb = getById(id);
        toolReleaseDb.setReleaseTime(toolRelease.getReleaseTime());
        toolReleaseDb.setReturnTime(toolRelease.getReturnTime());

        return toolReleaseDb;
    }
}
