package com.emontazysta.service;

import com.emontazysta.model.Employment;

import java.util.Date;
import java.util.List;

public interface EmploymentService {

    List<Employment> getAll();
    Employment getById(Long id);
    void add(Employment employment);
}
