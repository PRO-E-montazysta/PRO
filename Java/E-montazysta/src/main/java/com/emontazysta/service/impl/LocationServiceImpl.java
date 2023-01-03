package com.emontazysta.service.impl;

import com.emontazysta.model.Location;
import com.emontazysta.repository.LocationRepository;
import com.emontazysta.service.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository repository;

    @Override
    public List<Location> getAll() {
        return repository.findAll();
    }

    @Override
    public Location getById(Long id) {
        return repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void add(Location location) {
        repository.save(location);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void update(Long id, Location location) {
        Location updatedLocation = this.getById(id);
        updatedLocation.setName(location.getName());
        updatedLocation.setXCoordinate(location.getXCoordinate());
        updatedLocation.setYCoordinate(location.getYCoordinate());
        updatedLocation.setCity(location.getCity());
        updatedLocation.setStreet(location.getStreet());
        updatedLocation.setPropertyNumber(location.getPropertyNumber());
        updatedLocation.setApartmentNumber(location.getApartmentNumber());
        updatedLocation.setZipCode(location.getZipCode());

        repository.save(updatedLocation);
    }
}
