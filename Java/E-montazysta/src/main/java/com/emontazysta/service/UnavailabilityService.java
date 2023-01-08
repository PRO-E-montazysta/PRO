package com.emontazysta.service;

import com.emontazysta.model.Unavailability;
import java.util.List;

public interface UnavailabilityService {

    List<Unavailability> getAll();
    Unavailability getById(Long id);
    void add(Unavailability unavailability);
    void delete(Long id);
    void update(Long id, Unavailability unavailability);
}
