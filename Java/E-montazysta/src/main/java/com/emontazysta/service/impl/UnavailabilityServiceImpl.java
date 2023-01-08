package com.emontazysta.service.impl;

import com.emontazysta.model.Unavailability;
import com.emontazysta.repository.UnavailabilityRepository;
import com.emontazysta.service.UnavailabilityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class UnavailabilityServiceImpl implements UnavailabilityService {

    private final UnavailabilityRepository repository;

    @Override
    public List<Unavailability> getAll() {
        return repository.findAll();
    }

    @Override
    public Unavailability getById(Long id) {
        return repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void add(Unavailability unavailability) {
        repository.save(unavailability);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void update(Long id, Unavailability unavailability) {
        Unavailability updatedUnavailability = this.getById(id);
        updatedUnavailability.setTypeOfUnavailability(unavailability.getTypeOfUnavailability());
        updatedUnavailability.setDescription(unavailability.getDescription());
        updatedUnavailability.setUnavailableFrom(unavailability.getUnavailableFrom());
        updatedUnavailability.setUnavailableTo(unavailability.getUnavailableTo());

        repository.save(updatedUnavailability);
    }
}



