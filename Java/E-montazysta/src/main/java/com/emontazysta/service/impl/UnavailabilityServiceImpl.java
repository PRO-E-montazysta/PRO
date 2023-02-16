package com.emontazysta.service.impl;

import com.emontazysta.mapper.UnavailabilityMapper;
import com.emontazysta.model.Unavailability;
import com.emontazysta.model.dto.UnavailabilityDto;
import com.emontazysta.repository.UnavailabilityRepository;
import com.emontazysta.service.UnavailabilityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UnavailabilityServiceImpl implements UnavailabilityService {

    private final UnavailabilityRepository repository;
    private final UnavailabilityMapper unavailabilityMapper;

    @Override
    public List<UnavailabilityDto> getAll() {
        return repository.findAll().stream()
                .map(unavailabilityMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UnavailabilityDto getById(Long id) {
        Unavailability unavailability = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return unavailabilityMapper.toDto(unavailability);
    }

    @Override
    public UnavailabilityDto add(UnavailabilityDto unavailabilityDto) {
        Unavailability unavailability = unavailabilityMapper.toEntity(unavailabilityDto);
        return unavailabilityMapper.toDto(repository.save(unavailability));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public UnavailabilityDto update(Long id, UnavailabilityDto unavailabilityDto) {
        Unavailability updatedUnavailability = unavailabilityMapper.toEntity(unavailabilityDto);
        Unavailability unavailability = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        unavailability.setTypeOfUnavailability(updatedUnavailability.getTypeOfUnavailability());
        unavailability.setDescription(updatedUnavailability.getDescription());
        unavailability.setUnavailableFrom(updatedUnavailability.getUnavailableFrom());
        unavailability.setUnavailableTo(updatedUnavailability.getUnavailableTo());
        unavailability.setAssignedTo(updatedUnavailability.getAssignedTo());
        unavailability.setAssignedBy(updatedUnavailability.getAssignedBy());

        return unavailabilityMapper.toDto(repository.save(updatedUnavailability));
    }
}



