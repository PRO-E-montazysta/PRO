package com.emontazysta.service.impl;

import com.emontazysta.mapper.ElementReturnReleaseMapper;
import com.emontazysta.model.ElementReturnRelease;
import com.emontazysta.model.dto.ElementReturnReleaseDto;
import com.emontazysta.repository.ElementReturnReleaseRepository;
import com.emontazysta.service.ElementReturnReleaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ElementReturnReleaseServiceImpl implements ElementReturnReleaseService {

    private final ElementReturnReleaseRepository repository;
    private final ElementReturnReleaseMapper elementReturnReleaseMapper;

    @Override
    public List<ElementReturnReleaseDto> getAll() {
        return repository.findAll().stream()
                .map(elementReturnReleaseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ElementReturnReleaseDto getById(Long id) {
        ElementReturnRelease elementReturnRelease = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return elementReturnReleaseMapper.toDto(elementReturnRelease);
    }

    @Override
    public ElementReturnReleaseDto add(ElementReturnReleaseDto elementReturnReleaseDto) {
        ElementReturnRelease elementReturnRelease = elementReturnReleaseMapper.toEntity(elementReturnReleaseDto);
        return elementReturnReleaseMapper.toDto(repository.save(elementReturnRelease));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public ElementReturnReleaseDto update(Long id, ElementReturnReleaseDto elementReturnReleaseDto) {
        ElementReturnRelease updatedElementReturnRelease = elementReturnReleaseMapper.toEntity(elementReturnReleaseDto);
        ElementReturnRelease elementReturnReleaseDb = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        elementReturnReleaseDb.setReleaseTime(updatedElementReturnRelease.getReleaseTime());
        elementReturnReleaseDb.setReleasedQuantity(updatedElementReturnRelease.getReleasedQuantity());
        elementReturnReleaseDb.setReturnedQuantity(updatedElementReturnRelease.getReturnedQuantity());
        elementReturnReleaseDb.setReturnTime(updatedElementReturnRelease.getReturnTime());
        elementReturnReleaseDb.setServedBy(updatedElementReturnRelease.getServedBy());
        elementReturnReleaseDb.setElement(updatedElementReturnRelease.getElement());
        elementReturnReleaseDb.setDemandAdHoc(updatedElementReturnRelease.getDemandAdHoc());
        elementReturnReleaseDb.setOrderStage(updatedElementReturnRelease.getOrderStage());
        return elementReturnReleaseMapper.toDto(elementReturnReleaseDb);
    }
}
