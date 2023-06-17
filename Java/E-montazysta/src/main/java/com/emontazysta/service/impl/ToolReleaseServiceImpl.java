package com.emontazysta.service.impl;

import com.emontazysta.mapper.ToolReleaseMapper;
import com.emontazysta.model.ToolRelease;
import com.emontazysta.model.dto.ToolReleaseDto;
import com.emontazysta.repository.ToolReleaseRepository;
import com.emontazysta.repository.criteria.ToolReleaseCriteriaRepository;
import com.emontazysta.service.ToolReleaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ToolReleaseServiceImpl implements ToolReleaseService {

    private final ToolReleaseRepository repository;
    private final ToolReleaseMapper toolReleaseMapper;
    private final ToolReleaseCriteriaRepository criteriaRepo;

    @Override
    public List<ToolReleaseDto> getAll() {
        return repository.findAll().stream()
                .map(toolReleaseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ToolReleaseDto getById(Long id) {
        ToolRelease toolRelease = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return toolReleaseMapper.toDto(toolRelease);
    }

    @Override
    public ToolReleaseDto add(ToolReleaseDto toolReleaseDto) {
        ToolRelease toolRelease = toolReleaseMapper.toEntity(toolReleaseDto);
        toolRelease.setReleaseTime(LocalDateTime.now());
        return toolReleaseMapper.toDto(repository.save(toolRelease));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public ToolReleaseDto update(Long id, ToolReleaseDto toolReleaseDto) {

        ToolRelease updatedToolRelease = toolReleaseMapper.toEntity(toolReleaseDto);
        ToolRelease toolReleaseDb = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        toolReleaseDb.setReturnTime(updatedToolRelease.getReturnTime());
        toolReleaseDb.setReleasedBy(updatedToolRelease.getReleasedBy());
        toolReleaseDb.setTool(updatedToolRelease.getTool());
        toolReleaseDb.setDemandAdHoc(updatedToolRelease.getDemandAdHoc());
        toolReleaseDb.setOrderStage(updatedToolRelease.getOrderStage());

        return toolReleaseMapper.toDto(toolReleaseDb);
    }

    @Override
    public List<ToolReleaseDto> findAllFromCompany() {
        return criteriaRepo.findAllFromCompany();
    }
}
