package com.emontazysta.service.impl;

import com.emontazysta.mapper.SpecialistMapper;
import com.emontazysta.model.Specialist;
import com.emontazysta.model.dto.SpecialistDto;
import com.emontazysta.repository.SpecialistRepository;
import com.emontazysta.service.SpecialistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpecialistServiceImpl implements SpecialistService {

    private final SpecialistRepository repository;
    private final SpecialistMapper specialistMapper;

    @Override
    public List<SpecialistDto> getAll() {
        return repository.findAll().stream()
                .map(specialistMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SpecialistDto getById(Long id) {
        Specialist specialist = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return specialistMapper.toDto(specialist);
    }

    @Override
    public SpecialistDto add(SpecialistDto specialistDto) {
        Specialist specialist = specialistMapper.toEntity(specialistDto);
        return specialistMapper.toDto(repository.save(specialist));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
