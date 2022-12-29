package com.emontazysta.service;

import com.emontazysta.model.Manager;

import java.util.List;

public interface ManagerService {

    List<Manager> getAll();
    Manager getById(Long id);
    void add(Manager manager);
    void delete(Long id);
}
