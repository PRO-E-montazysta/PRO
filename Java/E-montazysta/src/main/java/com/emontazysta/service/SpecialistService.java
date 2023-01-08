package com.emontazysta.service;

import com.emontazysta.model.Specialist;

import java.util.List;

public interface SpecialistService {

    List<Specialist> getAll();
    Specialist getById(Long id);
    void add(Specialist specialist);
    void delete(Long id);
}
