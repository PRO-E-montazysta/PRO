package com.emontazysta.controller;

import com.emontazysta.model.Unavailability;
import com.emontazysta.service.UnavailabilityService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("unavailability")
public class UnavailabilityController {

    private final UnavailabilityService unavailabilityService;

    @GetMapping
    public List<Unavailability> getUnavailabilities() {
        return unavailabilityService.getAll();
    }

    @GetMapping("{id}")
    public Unavailability getUnavailability(@PathVariable("id") Long id) {
        return unavailabilityService.getById(id);
    }

    @PostMapping
    public void addUnavailability(@RequestBody Unavailability unavailability) {
        unavailabilityService.add(unavailability);
    }

    @DeleteMapping("{id}")
    public void deleteUnavailability(@PathVariable("id") Long id) {
        unavailabilityService.delete(id);
    }

    @PutMapping("{id}")
    public void updateUnavailability(@PathVariable("id") Long id,
                                     @RequestBody Unavailability unavailability) {
        unavailabilityService.update(id, unavailability);
    }
}
