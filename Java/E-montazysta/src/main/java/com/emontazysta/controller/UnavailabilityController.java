package com.emontazysta.controller;

import com.emontazysta.model.dto.UnavailabilityDto;
import com.emontazysta.service.UnavailabilityService;
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
@RequestMapping(value = API_BASE_CONSTANT + "/unavailabilities", produces = MediaType.APPLICATION_JSON_VALUE)
public class UnavailabilityController {

    private final UnavailabilityService unavailabilityService;

    @GetMapping("/all")
    @Operation(description = "Allows to get all Unavailabilities.", security = @SecurityRequirement(name = "bearer-key"))
    public List<UnavailabilityDto> getAll() {
        return unavailabilityService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(description = "Allows to get Unavailability by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public UnavailabilityDto getById(@PathVariable("id") Long id) {
        return unavailabilityService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add new Unavailability.", security = @SecurityRequirement(name = "bearer-key"))
    public UnavailabilityDto add(@Valid @RequestBody UnavailabilityDto unavailability) {
        return unavailabilityService.add(unavailability);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Allows to delete Unavailability by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void delete(@PathVariable("id") Long id) {
        unavailabilityService.delete(id);
    }

    @PutMapping("/{id}")
    @Operation(description = "Allows to update Unavailability by given Id, and Unavailability.", security = @SecurityRequirement(name = "bearer-key"))

    public UnavailabilityDto update(@PathVariable("id") Long id,
                                     @Valid @RequestBody UnavailabilityDto unavailability) {
        return unavailabilityService.update(id, unavailability);
    }
}
