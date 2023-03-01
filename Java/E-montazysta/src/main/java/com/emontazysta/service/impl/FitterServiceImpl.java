package com.emontazysta.service.impl;

import com.emontazysta.mapper.FitterMapper;
import com.emontazysta.model.Fitter;
import com.emontazysta.model.dto.FitterDto;
import com.emontazysta.repository.FitterRepository;
import com.emontazysta.service.FitterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FitterServiceImpl implements FitterService {

    private final FitterRepository repository;
    private final FitterMapper fitterMapper;

    @Override
    public List<FitterDto> getAll() {
        return repository.findAll().stream()
                .map(fitterMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public FitterDto getById(Long id) {
        Fitter fitter = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return fitterMapper.toDto(fitter);
    }

    @Override
    public FitterDto add(FitterDto fitterDto) {
        Fitter fitter = fitterMapper.toEntity(fitterDto);
        return fitterMapper.toDto(repository.save(fitter));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
