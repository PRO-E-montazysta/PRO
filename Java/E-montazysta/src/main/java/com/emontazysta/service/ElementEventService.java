package com.emontazysta.service;

import com.emontazysta.model.ElementEvent;

import java.util.List;

public interface ElementEventService {

    List<ElementEvent> getAll();
     ElementEvent getById(Long id);
     void add(ElementEvent event);
     void delete(Long id);
}
