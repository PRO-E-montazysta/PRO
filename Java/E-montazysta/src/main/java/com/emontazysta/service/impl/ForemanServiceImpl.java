package com.emontazysta.service.impl;

import com.emontazysta.mapper.ForemanMapper;
import com.emontazysta.model.Foreman;
import com.emontazysta.model.dto.ForemanDto;
import com.emontazysta.repository.ForemanRepository;
import com.emontazysta.service.ForemanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ForemanServiceImpl implements ForemanService {

    private final ForemanRepository repository;
    private final ForemanMapper foremanMapper;

    @Override
    public List<ForemanDto> getAll() {
        return repository.findAll().stream()
                .map(foremanMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ForemanDto getById(Long id) {
        Foreman foreman = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return foremanMapper.toDto(foreman);
    }

    @Override
    public ForemanDto add(ForemanDto foremanDto) {
        Foreman foreman = foremanMapper.toEntity(foremanDto);
        return foremanMapper.toDto(repository.save(foreman));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
