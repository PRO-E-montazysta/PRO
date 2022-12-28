package com.emontazysta.controller;

import com.emontazysta.data.LocationRequest;
import com.emontazysta.model.Location;
import com.emontazysta.service.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("location")
public class LocationController {

    private final LocationService locationService;

    @GetMapping
    public List<Location> getLocations() {
        return locationService.getLocations();
    }

    @GetMapping("{locationId}")
    public Location getLocation(@PathVariable("locationId") Long locationId) {
        return locationService.getLocation(locationId);
    }

    @PostMapping
    public void addLocation(@RequestBody LocationRequest locationToAdd) {
        locationService.addLocation(locationToAdd);
    }

    @DeleteMapping("{locationId}")
    public void deleteLocation(@PathVariable("locationId") Long locationId) {
        locationService.deleteLocation(locationId);
    }

    @PutMapping("{locationId}")
    public void updateLocation(@PathVariable("locationId") Long locationId,
                               @RequestBody LocationRequest locationToUpdate) {
        locationService.updateLocation(locationId, locationToUpdate);
    }
}