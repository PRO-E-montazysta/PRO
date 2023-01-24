package com.emontazysta.service.impl;

import com.emontazysta.model.ElementInWarehouse;
import com.emontazysta.repository.ElementInWarehouseRepository;
import com.emontazysta.service.ElementInWarehouseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ElementInWarehouseServiceImpl implements ElementInWarehouseService {
    private final ElementInWarehouseRepository elementInWarehouseRepository;

    public List<ElementInWarehouse> getAll(){
        return elementInWarehouseRepository.findAll();
    }

    public ElementInWarehouse getById(Long id) {
        return elementInWarehouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Element with id " + id + " not found in warehouse!"));

    }

    public void add(ElementInWarehouse elementInWarehouse) {
        elementInWarehouseRepository.save(elementInWarehouse);
    }

    public void delete(Long id) {
        elementInWarehouseRepository.deleteById(id);
    }
}
