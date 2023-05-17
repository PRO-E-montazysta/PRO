package com.emontazysta.service.impl;

import com.emontazysta.mapper.ElementInWarehouseMapper;
import com.emontazysta.model.Element;
import com.emontazysta.model.ElementInWarehouse;
import com.emontazysta.model.dto.ElementInWarehouseDto;
import com.emontazysta.repository.ElementInWarehouseRepository;
import com.emontazysta.service.ElementInWarehouseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ElementInWarehouseServiceImpl implements ElementInWarehouseService {

    private final ElementInWarehouseRepository elementInWarehouseRepository;
    private final ElementInWarehouseMapper elementInWarehouseMapper;

    @Override
    public List<ElementInWarehouseDto> getAll(){
        return elementInWarehouseRepository.findAll().stream()
                .map(elementInWarehouseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ElementInWarehouseDto getById(Long id) {
        ElementInWarehouse elementInWarehouse = elementInWarehouseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Element with id " + id + " not found in warehouse!"));
        return elementInWarehouseMapper.toDto(elementInWarehouse);

    }

    @Override
    public ElementInWarehouseDto add(ElementInWarehouseDto elementInWarehouseDto) {
        ElementInWarehouse elementInWarehouse = elementInWarehouseMapper.toEntity(elementInWarehouseDto);
        return elementInWarehouseMapper.toDto(elementInWarehouseRepository.save(elementInWarehouse));
    }

    @Override
    public void delete(Long id) {
        elementInWarehouseRepository.deleteById(id);
    }

    @Override
    public ElementInWarehouseDto update(Long id, ElementInWarehouseDto elementInWarehouseDto) {
        ElementInWarehouse updatedElementInWarehouse = elementInWarehouseMapper.toEntity(elementInWarehouseDto);
        ElementInWarehouse elementInWarehouse = elementInWarehouseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Element with id " + id + " not found in warehouse!"));
        elementInWarehouse.setInWarehouseCount(updatedElementInWarehouse.getInWarehouseCount());
        elementInWarehouse.setInUnitCount(updatedElementInWarehouse.getInUnitCount());
        elementInWarehouse.setRack(updatedElementInWarehouse.getRack());
        elementInWarehouse.setShelf(updatedElementInWarehouse.getShelf());
        elementInWarehouse.setElement(updatedElementInWarehouse.getElement());
        elementInWarehouse.setWarehouse(updatedElementInWarehouse.getWarehouse());
        return elementInWarehouseMapper.toDto(elementInWarehouseRepository.save(elementInWarehouse));
    }

    @Override
    public void changeInWarehouseCountByQuantity(Element element, Long warehouseId, int quantity) {
        Optional<ElementInWarehouse> elementInWarehouseOptional = element.getElementInWarehouses().stream()
                .filter(o -> o.getWarehouse().getId().equals(warehouseId)).findFirst();
        if(elementInWarehouseOptional.isPresent()) {
            ElementInWarehouse elementInWarehouse = elementInWarehouseOptional.get();
            elementInWarehouse.setInWarehouseCount(elementInWarehouse.getInWarehouseCount() + quantity);
            elementInWarehouseRepository.save(elementInWarehouse);
        }else {
            throw new EntityNotFoundException();
        }
    }
}
