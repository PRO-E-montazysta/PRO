package com.emontazysta.service;

import com.emontazysta.model.SalesRepresentative;

import java.util.List;

public interface SalesRepresentativeService {

    List<SalesRepresentative> getAll();
    SalesRepresentative getById(Long id);
    void add(SalesRepresentative salesRepresentative);
    void delete(Long id);
}
