package com.emontazysta.service;

import com.emontazysta.model.Location;
import com.emontazysta.repository.LocationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public List<Location> getLocations() {
        return locationRepository.findAll();
    }

    public Location getLocation(Long id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location with id " + id + " not found!"));
    }

    public void addLocation(Location request) {
        locationRepository.save(request);
    }

    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }

    public void updateLocation(Long id, Location updatedLocation) {
        updatedLocation.setId(id);
        locationRepository.save(updatedLocation);
    }
}
