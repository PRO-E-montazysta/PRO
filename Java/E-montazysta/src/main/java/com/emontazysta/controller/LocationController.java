package com.emontazysta.controller;

import com.emontazysta.model.dto.LocationDto;
import com.emontazysta.service.impl.LocationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@AllArgsConstructor
@RequestMapping(value = API_BASE_CONSTANT + "/locations", produces = MediaType.APPLICATION_JSON_VALUE)
public class LocationController {

    private final LocationServiceImpl locationService;

    @GetMapping("/all")
    @Operation(description = "Allows to get all Locations.", security = @SecurityRequirement(name = "bearer-key"))
    public List<LocationDto> getAll() {
        return locationService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(description = "Allows to get Location by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public LocationDto getById(@PathVariable("id") Long id) {
        return locationService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add new Location.", security = @SecurityRequirement(name = "bearer-key"))
    public LocationDto add(@Valid @RequestBody LocationDto location) {
        return locationService.add(location);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Allows to delete Location by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void deleteById(@PathVariable("id") Long id) {
        locationService.delete(id);
    }

    @PutMapping("/{id}")
    @Operation(description = "Allows to update Location by given Id, and Location.", security = @SecurityRequirement(name = "bearer-key"))
    public LocationDto update(@PathVariable("id") Long id,
                               @Valid @RequestBody LocationDto location) {
        return locationService.update(id, location);
    }
}
