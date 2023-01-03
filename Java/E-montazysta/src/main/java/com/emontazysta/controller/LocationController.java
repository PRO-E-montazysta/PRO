package com.emontazysta.controller;

import com.emontazysta.model.Location;
import com.emontazysta.service.impl.LocationServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@AllArgsConstructor
@RequestMapping(API_BASE_CONSTANT + "/locations")
public class LocationController {

    private final LocationServiceImpl locationService;

    @GetMapping
    public List<Location> getLocations() {
        return locationService.getAll();
    }

    @GetMapping("{id}")
    public Location getLocation(@PathVariable("id") Long id) {
        return locationService.getById(id);
    }

    @PostMapping
    public void addLocation(@RequestBody Location location) {
        locationService.add(location);
    }

    @DeleteMapping("{id}")
    public void deleteLocation(@PathVariable("id") Long id) {
        locationService.delete(id);
    }

    @PutMapping("{id}")
    public void updateLocation(@PathVariable("id") Long id,
                               @RequestBody Location location) {
        locationService.update(id, location);
    }
}
