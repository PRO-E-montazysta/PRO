package com.emontazysta.controller;

import com.emontazysta.data.UnavailabilityRequest;
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
        return unavailabilityService.getUnavailabilities();
    }

    @GetMapping("{unavailabilityId}")
    public Unavailability getUnavailability(@PathVariable("unavailabilityId") Long unavailabilityId) {
        return unavailabilityService.getUnavailability(unavailabilityId);
    }

    @PostMapping
    public void addUnavailability(@RequestBody UnavailabilityRequest unavailabilityToAdd) {
        unavailabilityService.addUnavailability(unavailabilityToAdd);
    }

    @DeleteMapping("{unavailabilityId}")
    public void deleteUnavailability(@PathVariable("unavailabilityId") Long unavailabilityId) {
        unavailabilityService.deleteUnavailability(unavailabilityId);
    }

    @PutMapping("{unavailabilityId}")
    public void updateUnavailability(@PathVariable("unavailabilityId") Long unavailabilityId,
                                     @RequestBody UnavailabilityRequest unavailabilityToUpdate) {
        unavailabilityService.updateUnavailability(unavailabilityId, unavailabilityToUpdate);
    }
}
