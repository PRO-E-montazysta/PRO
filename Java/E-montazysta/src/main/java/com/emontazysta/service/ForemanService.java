package com.emontazysta.service;

import com.emontazysta.model.Foreman;

import java.util.List;

public interface ForemanService {

    List<Foreman> getAll();
    Foreman getById(Long id);
    void add(Foreman foreman);
    void delete(Long id);
}
