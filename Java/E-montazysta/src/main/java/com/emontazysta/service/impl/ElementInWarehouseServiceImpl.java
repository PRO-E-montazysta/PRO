package com.emontazysta.service.impl;

import com.emontazysta.enums.Role;
import com.emontazysta.mapper.ElementInWarehouseMapper;
import com.emontazysta.model.Element;
import com.emontazysta.model.ElementInWarehouse;
import com.emontazysta.model.dto.ElementInWarehouseDto;
import com.emontazysta.model.dto.filterDto.ElementInWarehouseFilterDto;
import com.emontazysta.model.searchcriteria.ElementInWarehouseSearchCriteria;
import com.emontazysta.repository.ElementInWarehouseRepository;
import com.emontazysta.repository.ElementRepository;
import com.emontazysta.repository.criteria.ElementInWarehouseCriteriaRepository;
import com.emontazysta.service.ElementInWarehouseService;
import com.emontazysta.util.AuthUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ElementInWarehouseServiceImpl implements ElementInWarehouseService {

    private final ElementInWarehouseRepository elementInWarehouseRepository;
    private final ElementInWarehouseMapper elementInWarehouseMapper;
    private final ElementInWarehouseCriteriaRepository elementInWarehouseCriteriaRepository;
    private final ElementRepository elementRepository;
    private final AuthUtils authUtils;

    @Override
    public ElementInWarehouseDto getById(Long id) {
        ElementInWarehouse elementInWarehouse = elementInWarehouseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Element with id " + id + " not found in warehouse!"));
        return elementInWarehouseMapper.toDto(elementInWarehouse);

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
        if(authUtils.getLoggedUser().getRoles().contains(Role.WAREHOUSE_MANAGER)) {
            elementInWarehouse.setRack(updatedElementInWarehouse.getRack());
            elementInWarehouse.setShelf(updatedElementInWarehouse.getShelf());
        }

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
