package com.emontazysta.service.impl;

import com.emontazysta.mapper.SalesRepresentativeMapper;
import com.emontazysta.model.SalesRepresentative;
import com.emontazysta.model.dto.SalesRepresentativeDto;
import com.emontazysta.repository.SalesRepresentativeRepository;
import com.emontazysta.service.SalesRepresentativeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesRepresentativeServiceImpl implements SalesRepresentativeService {

    private final SalesRepresentativeRepository repository;
    private final SalesRepresentativeMapper salesRepresentativeMapper;

    @Override
    public List<SalesRepresentativeDto> getAll() {
        return repository.findAll().stream()
                .map(salesRepresentativeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SalesRepresentativeDto getById(Long id) {
        SalesRepresentative salesRepresentative = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return salesRepresentativeMapper.toDto(salesRepresentative);
    }

    @Override
    public SalesRepresentativeDto add(SalesRepresentativeDto salesRepresentativeDto) {
        SalesRepresentative salesRepresentative = salesRepresentativeMapper.toEntity(salesRepresentativeDto);
        return salesRepresentativeMapper.toDto(repository.save(salesRepresentative));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
