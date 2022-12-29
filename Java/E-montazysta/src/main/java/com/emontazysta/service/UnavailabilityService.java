package com.emontazysta.service;

import com.emontazysta.data.UnavailabilityRequest;
import com.emontazysta.model.Unavailability;
import com.emontazysta.repository.UnavailabilityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
@AllArgsConstructor
public class UnavailabilityService {

    private final UnavailabilityRepository unavailabilityRepository;

    public List<Unavailability> getUnavailabilities() {
        return unavailabilityRepository.findAll();
    }

    public Unavailability getUnavailability(Long id) {
        return unavailabilityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unavailability with id " + id + " not found!"));
    }

    public void addUnavailability(UnavailabilityRequest unavailabilityToAdd) {
        Unavailability newUnavailability = new Unavailability();
        newUnavailability.setTypeOfUnavailability(unavailabilityToAdd.getTypeOfUnavailability());
        newUnavailability.setDescription(unavailabilityToAdd.getDescription());
        newUnavailability.setUnavailableFrom(unavailabilityToAdd.getUnavailableFrom());
        newUnavailability.setUnavailableTo(unavailabilityToAdd.getUnavailableTo());

        unavailabilityRepository.save(newUnavailability);
    }

    public void deleteUnavailability(Long id) {
        unavailabilityRepository.deleteById(id);
    }

    public void updateUnavailability(Long id, UnavailabilityRequest unavailabilityToUpdate) {
        Unavailability updatedUnavailability = this.getUnavailability(id);
        updatedUnavailability.setTypeOfUnavailability(unavailabilityToUpdate.getTypeOfUnavailability());
        updatedUnavailability.setDescription(unavailabilityToUpdate.getDescription());
        updatedUnavailability.setUnavailableFrom(unavailabilityToUpdate.getUnavailableFrom());
        updatedUnavailability.setUnavailableTo(unavailabilityToUpdate.getUnavailableTo());


        unavailabilityRepository.save(updatedUnavailability);
    }
}
