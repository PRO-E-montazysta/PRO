package com.emontazysta.service;

import com.emontazysta.model.ElementReturnRelease;

import java.util.List;

public interface ElementReturnReleaseService {

    List<ElementReturnRelease> getAll();
    ElementReturnRelease getById(Long id);
    void add(ElementReturnRelease elementReturnRelease);
    void delete(Long id);
    ElementReturnRelease update(Long id, ElementReturnRelease elementReturnRelease);
}
