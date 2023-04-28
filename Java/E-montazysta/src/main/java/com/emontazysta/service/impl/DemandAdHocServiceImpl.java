package com.emontazysta.service.impl;

import com.emontazysta.mapper.DemandAdHocMapper;
import com.emontazysta.mapper.ElementsPlannedNumberMapper;
import com.emontazysta.mapper.ToolsPlannedNumberMapper;
import com.emontazysta.model.DemandAdHoc;
import com.emontazysta.model.ElementsPlannedNumber;
import com.emontazysta.model.ToolsPlannedNumber;
import com.emontazysta.model.dto.DemandAdHocDto;
import com.emontazysta.model.dto.ElementsPlannedNumberDto;
import com.emontazysta.model.dto.ToolsPlannedNumberDto;
import com.emontazysta.model.dto.filterDto.DemandAdHocFilterDto;
import com.emontazysta.model.dto.filterDto.DemandAdHocWithToolsAndElementsDto;
import com.emontazysta.model.searchcriteria.DemandAdHocSearchCriteria;
import com.emontazysta.repository.DemandAdHocRepository;
import com.emontazysta.repository.ElementsPlannedNumberRepository;
import com.emontazysta.repository.ToolsPlannedNumberRepository;
import com.emontazysta.repository.criteria.DemandAdHocCriteriaRepository;
import com.emontazysta.service.DemandAdHocService;
import com.emontazysta.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DemandAdHocServiceImpl implements DemandAdHocService {

    private final DemandAdHocRepository repository;
    private final DemandAdHocMapper demandAdHocMapper;
    private final DemandAdHocCriteriaRepository demandAdHocCriteriaRepository;
    private final AuthUtils authUtils;
    private final ToolsPlannedNumberRepository toolsPlannedNumberRepository;
    private final ToolsPlannedNumberMapper toolsPlannedNumberMapper;
    private final ElementsPlannedNumberRepository elementsPlannedNumberRepository;
    private final ElementsPlannedNumberMapper elementsPlannedNumberMapper;

    @Override
    public List<DemandAdHocDto> getAll() {
        return repository.findAll().stream()
                .map(demandAdHocMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DemandAdHocDto getById(Long id) {

        DemandAdHoc demandAdHoc = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return demandAdHocMapper.toDto(demandAdHoc);
    }

    @Override
    public DemandAdHocDto add(DemandAdHocDto demandAdHocDto) {
        demandAdHocDto.setToolReleases(new ArrayList<>());
        demandAdHocDto.setElementReturnReleases(new ArrayList<>());
        demandAdHocDto.setCreatedById(authUtils.getLoggedUser().getId());
        demandAdHocDto.setListOfToolsPlannedNumber(demandAdHocDto.getListOfToolsPlannedNumber() == null ? new ArrayList<>() : demandAdHocDto.getListOfToolsPlannedNumber());
        demandAdHocDto.setListOfElementsPlannedNumber(demandAdHocDto.getListOfElementsPlannedNumber() == null ? new ArrayList<>() : demandAdHocDto.getListOfElementsPlannedNumber());
        demandAdHocDto.setCreatedAt(LocalDateTime.now());
        DemandAdHoc demandAdHoc = demandAdHocMapper.toEntity(demandAdHocDto);

        return demandAdHocMapper.toDto(repository.save(demandAdHoc));
    }

    @Override
    public DemandAdHocDto addWithToolsAndElements(DemandAdHocWithToolsAndElementsDto demandAdHocDto) {
        demandAdHocDto.setToolReleases(new ArrayList<>());
        demandAdHocDto.setElementReturnReleases(new ArrayList<>());
        demandAdHocDto.setCreatedById(authUtils.getLoggedUser().getId());
        demandAdHocDto.setListOfToolsPlannedNumber(demandAdHocDto.getListOfToolsPlannedNumber() == null ? new ArrayList<>() : demandAdHocDto.getListOfToolsPlannedNumber());
        demandAdHocDto.setListOfElementsPlannedNumber(demandAdHocDto.getListOfElementsPlannedNumber() == null ? new ArrayList<>() : demandAdHocDto.getListOfElementsPlannedNumber());
        demandAdHocDto.setCreatedAt(LocalDateTime.now());
        DemandAdHoc demandAdHoc = demandAdHocMapper.toEntity(demandAdHocDto);

        DemandAdHoc savedDemandAdHoc = repository.save(demandAdHoc);

        if(!demandAdHoc.getListOfToolsPlannedNumber().isEmpty()) {
            for (ToolsPlannedNumberDto toolsPlannedNumberDto : demandAdHocDto.getListOfToolsPlannedNumber()) {
                toolsPlannedNumberDto.setOrderStageId(savedDemandAdHoc.getId());
                ToolsPlannedNumber toolsPlannedNumber = toolsPlannedNumberMapper.toEntity(toolsPlannedNumberDto);
                toolsPlannedNumberRepository.save(toolsPlannedNumber);
            }
        }

        if(!demandAdHoc.getListOfElementsPlannedNumber().isEmpty()) {
            for (ElementsPlannedNumberDto elementsPlannedNumberDto : demandAdHocDto.getListOfElementsPlannedNumber()) {
                elementsPlannedNumberDto.setOrderStageId(savedDemandAdHoc.getId());
                ElementsPlannedNumber elementsPlannedNumber = elementsPlannedNumberMapper.toEntity(elementsPlannedNumberDto);
                elementsPlannedNumberRepository.save(elementsPlannedNumber);
            }
        }

        return getById(savedDemandAdHoc.getId());
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public DemandAdHocDto update(Long id, DemandAdHocWithToolsAndElementsDto demandAdHocDto) {
        DemandAdHoc demandAdHocDb = repository.findById(id).orElseThrow(EntityNotFoundException::new);

        demandAdHocDb.setDescription(demandAdHocDto.getDescription());
        demandAdHocDb.setRealisationTime(demandAdHocDto.getRealisationTime());

        return demandAdHocMapper.toDto(demandAdHocDb);
    }

    @Override
    public List<DemandAdHocFilterDto> getFiltered(DemandAdHocSearchCriteria demandAdHocSearchCriteria) {
        return demandAdHocCriteriaRepository.findAllWithFilters(demandAdHocSearchCriteria);
    }
}
