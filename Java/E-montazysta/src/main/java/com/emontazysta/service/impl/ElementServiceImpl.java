package com.emontazysta.service.impl;

import com.emontazysta.model.Element;
import com.emontazysta.model.Tool;
import com.emontazysta.repository.ElementRepository;
import com.emontazysta.service.ElementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ElementServiceImpl implements ElementService {

    private final ElementRepository repository;


    @Override
    public List<Element> getAll() {
        return repository.findAll();
    }

    @Override
    public Element getById(Long id) {
        return repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Element getByCode(String code) {
        Element response = repository.findByCode(code);
        if(response == null)
            throw new EntityNotFoundException();
        else
            return response;
    }

    @Override
    public void add(Element element) {
        element.setCode(UUID.randomUUID().toString());
        repository.save(element);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void update(Long id, Element element) {
        Element updatedElement = this.getById(id);
        updatedElement.setName(element.getName());
        updatedElement.setTypeOfUnit(element.getTypeOfUnit());
        updatedElement.setQuantityInUnit(element.getQuantityInUnit());

        repository.save(updatedElement);
    }
}
