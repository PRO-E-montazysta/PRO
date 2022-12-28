package com.emontazysta.service.impl;

import com.emontazysta.model.Manager;
import com.emontazysta.repository.ManagerRepository;
import com.emontazysta.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    private final ManagerRepository repository;

    @Override
    public List<Manager> getAll() {
        return repository.findAll();
    }

    @Override
    public Manager getById(Long id) {
        return repository.findById(id)
                         .orElseThrow(EntityExistsException::new);
    }

    @Override
    public void add(Manager manager) {
        repository.save(manager);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
