package com.emontazysta.service;

import com.emontazysta.model.Element;

import java.util.List;

public interface ElementService {

    List<Element> getAll();
    Element getById(Long id);
    Element getByCode(String code);
    void add(Element element);
    void delete(Long id);
}
