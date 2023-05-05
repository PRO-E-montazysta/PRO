package com.emontazysta.service.impl;

import com.emontazysta.mapper.ElementsPlannedNumberMapper;
import com.emontazysta.model.ElementsPlannedNumber;
import com.emontazysta.model.dto.ElementsDtoPlannedNumberDto;
import com.emontazysta.model.dto.ElementsPlannedNumberDto;
import com.emontazysta.repository.ElementsPlannedNumberRepository;
import com.emontazysta.service.ElementsPlannedNumberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ElementsPlannedServiceImpl implements ElementsPlannedNumberService {

    private final ElementsPlannedNumberRepository elementsPlannedNumberRepository;
    private final ElementsPlannedNumberMapper elementsPlannedNumberMapper;
    @Override
    public List<ElementsPlannedNumberDto> getAll() {
        return elementsPlannedNumberRepository.findAll().stream()
                .map(elementsPlannedNumberMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ElementsDtoPlannedNumberDto getById(Long id) {
        ElementsPlannedNumber elementsPlannedNumber = elementsPlannedNumberRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return elementsPlannedNumberMapper.toDtoWithElementDto(elementsPlannedNumber);
    }

    @Override
    public ElementsPlannedNumberDto add(ElementsPlannedNumberDto elementsPlannedNumberDto) {
        ElementsPlannedNumber elementsPlannedNumber = elementsPlannedNumberRepository.save(elementsPlannedNumberMapper.toEntity(elementsPlannedNumberDto));
        return elementsPlannedNumberMapper.toDto(elementsPlannedNumber);
    }

    @Override
    public void delete(Long id) {
        elementsPlannedNumberRepository.deleteById(id);
    }

    @Override
    public ElementsPlannedNumberDto update(Long id, ElementsPlannedNumberDto elementsPlannedNumberDto) {
        ElementsPlannedNumber updatedElementPlannedNumber = elementsPlannedNumberMapper.toEntity(elementsPlannedNumberDto);
        ElementsPlannedNumber elementsPlannedNumber = elementsPlannedNumberRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        elementsPlannedNumber.setElement(updatedElementPlannedNumber.getElement());
        elementsPlannedNumber.setNumberOfElements(updatedElementPlannedNumber.getNumberOfElements());
        elementsPlannedNumber.setOrderStage(updatedElementPlannedNumber.getOrderStage());
        return elementsPlannedNumberMapper.toDto(elementsPlannedNumberRepository.save(elementsPlannedNumber));
    }
}
