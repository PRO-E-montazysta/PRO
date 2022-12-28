package com.emontazysta.service;

import com.emontazysta.data.LocationRequest;
import com.emontazysta.model.Location;
import com.emontazysta.repositoriy.LocationRepository;
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

    public void addLocation(LocationRequest locationToAdd) {
        Location newLocation = new Location();
        newLocation.setName(locationToAdd.getName());
        newLocation.setXCoordinate(locationToAdd.getXCoordinate());
        newLocation.setYCoordinate(locationToAdd.getYCoordinate());
        newLocation.setCity(locationToAdd.getCity());
        newLocation.setStreet(locationToAdd.getStreet());
        newLocation.setPropertyNumber(locationToAdd.getPropertyNumber());
        newLocation.setApartmentNumber(locationToAdd.getApartmentNumber());
        newLocation.setZipCode(locationToAdd.getZipCode());

        locationRepository.save(newLocation);
    }

    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }

    public void updateLocation(Long id, LocationRequest locationToUpdate) {
        Location updatedLocation = this.getLocation(id);
        updatedLocation.setName(locationToUpdate.getName());
        updatedLocation.setXCoordinate(locationToUpdate.getXCoordinate());
        updatedLocation.setYCoordinate(locationToUpdate.getYCoordinate());
        updatedLocation.setCity(locationToUpdate.getCity());
        updatedLocation.setStreet(locationToUpdate.getStreet());
        updatedLocation.setPropertyNumber(locationToUpdate.getPropertyNumber());
        updatedLocation.setApartmentNumber(locationToUpdate.getApartmentNumber());
        updatedLocation.setZipCode(locationToUpdate.getZipCode());

        locationRepository.save(updatedLocation);
    }
}
