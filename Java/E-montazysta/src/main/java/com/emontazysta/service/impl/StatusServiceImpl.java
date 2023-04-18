package com.emontazysta.service.impl;

import com.emontazysta.model.AppUser;
import com.emontazysta.model.Unavailability;
import com.emontazysta.repository.UnavailabilityRepository;
import com.emontazysta.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService {

    private final UnavailabilityRepository unavailabilityRepository;

    @Override
    public Unavailability checkUnavailability(AppUser appUser) {
        LocalDateTime now = LocalDateTime.now();
        List<Unavailability> unavailabilities = unavailabilityRepository.findAllByAssignedTo(appUser);
        return unavailabilities.stream()
                .filter(unavailability -> now.isAfter(unavailability.getUnavailableFrom()) && now.isBefore(unavailability.getUnavailableTo()))
                .findFirst()
                .orElse(null);
    }
}
