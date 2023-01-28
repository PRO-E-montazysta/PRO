package com.emontazysta.controller;

import com.emontazysta.mapper.UnavailabilityMapper;
import com.emontazysta.model.Unavailability;
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
import java.util.stream.Collectors;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@AllArgsConstructor
@RequestMapping(value = API_BASE_CONSTANT + "/unavailabilities", produces = MediaType.APPLICATION_JSON_VALUE)
public class UnavailabilityController {

    private final UnavailabilityService unavailabilityService;

    @GetMapping("/all")
    @Operation(description = "Allows to get all Unavailabilities.", security = @SecurityRequirement(name = "bearer-key"))
    public List<UnavailabilityDto> getAll() {
        return unavailabilityService.getAll().stream()
                .map(UnavailabilityMapper::unavailabilityToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(description = "Allows to get Unavailability by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public UnavailabilityDto getById(@PathVariable("id") Long id) {
        return UnavailabilityMapper.unavailabilityToDto(unavailabilityService.getById(id));
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add new Unavailability.", security = @SecurityRequirement(name = "bearer-key"))
    public void add(@Valid @RequestBody Unavailability unavailability) {
        unavailabilityService.add(unavailability);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Allows to delete Unavailability by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void delete(@PathVariable("id") Long id) {
        unavailabilityService.delete(id);
    }

    @PutMapping("/{id}")
    @Operation(description = "Allows to update Unavailability by given Id, and Unavailability.", security = @SecurityRequirement(name = "bearer-key"))

    public void update(@PathVariable("id") Long id,
                                     @Valid @RequestBody Unavailability unavailability) {
        unavailabilityService.update(id, unavailability);
    }
}
