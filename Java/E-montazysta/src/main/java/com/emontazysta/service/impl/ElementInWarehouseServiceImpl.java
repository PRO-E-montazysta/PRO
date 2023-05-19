package com.emontazysta.service.impl;

import com.emontazysta.mapper.ElementInWarehouseMapper;
import com.emontazysta.model.ElementInWarehouse;
import com.emontazysta.model.dto.ElementInWarehouseDto;
import com.emontazysta.model.dto.filterDto.ElementInWarehouseFilterDto;
import com.emontazysta.model.searchcriteria.ElementInWarehouseSearchCriteria;
import com.emontazysta.repository.ElementInWarehouseRepository;
import com.emontazysta.repository.ElementRepository;
import com.emontazysta.repository.criteria.ElementInWarehouseCriteriaRepository;
import com.emontazysta.service.ElementInWarehouseService;
import com.emontazysta.service.ElementService;
import com.emontazysta.util.AuthUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ElementInWarehouseServiceImpl implements ElementInWarehouseService {

    private final ElementInWarehouseRepository elementInWarehouseRepository;
    private final ElementInWarehouseMapper elementInWarehouseMapper;
    private final ElementInWarehouseCriteriaRepository elementInWarehouseCriteriaRepository;
    private final ElementRepository elementRepository;
    private final AuthUtils authUtils;

    //TO DELETE
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

    //TO DELETE
    @Override
    public ElementInWarehouseDto add(ElementInWarehouseDto elementInWarehouseDto) {
        ElementInWarehouse elementInWarehouse = elementInWarehouseMapper.toEntity(elementInWarehouseDto);
        return elementInWarehouseMapper.toDto(elementInWarehouseRepository.save(elementInWarehouse));
    }

    //TO DELETE
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

        return elementInWarehouseMapper.toDto(elementInWarehouseRepository.save(elementInWarehouse));
    }

    @Override
    public List<ElementInWarehouseFilterDto> getFilteredWarehouseCount(Long elementId, ElementInWarehouseSearchCriteria elementInWarehouseSearchCriteria) {
        if(elementRepository.getReferenceById(elementId).getElementInWarehouses().get(0).getWarehouse().getCompany().getId().equals(authUtils.getLoggedUserCompanyId())) {
            elementInWarehouseSearchCriteria.setElementId(String.valueOf(elementId));
            return elementInWarehouseCriteriaRepository.findElementInWarehouseCounts(elementInWarehouseSearchCriteria);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
