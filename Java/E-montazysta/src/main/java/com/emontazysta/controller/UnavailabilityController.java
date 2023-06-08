package com.emontazysta.controller;

import com.emontazysta.model.dto.UnavailabilityToCalendarDto;
import com.emontazysta.model.dto.UnavailabilityDto;
import com.emontazysta.model.dto.UnavailabilityWithLocalDateDto;
import com.emontazysta.model.dto.filterDto.UnavailabilityFilterDto;
import com.emontazysta.model.searchcriteria.UnavailabilitySearchCriteria;
import com.emontazysta.service.UnavailabilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@AllArgsConstructor
@RequestMapping(value = API_BASE_CONSTANT + "/unavailabilities", produces = MediaType.APPLICATION_JSON_VALUE)
public class UnavailabilityController {

    private final UnavailabilityService unavailabilityService;

    //TO DELETE
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
    @PreAuthorize("hasAuthority('SCOPE_MANAGER')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add new Unavailability.", security = @SecurityRequirement(name = "bearer-key"))
    public UnavailabilityDto add(@Valid @RequestBody UnavailabilityWithLocalDateDto unavailability) {
        return unavailabilityService.addWithLocalDate(unavailability);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Allows to delete Unavailability by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void delete(@PathVariable("id") Long id) {
        unavailabilityService.delete(id);
    }

    @PutMapping("/{id}")
    @Operation(description = "Allows to update Unavailability by given Id, and Unavailability.", security = @SecurityRequirement(name = "bearer-key"))
    public UnavailabilityDto update(@PathVariable("id") Long id,
                                     @Valid @RequestBody UnavailabilityWithLocalDateDto unavailability) {
        return unavailabilityService.updateWithLocalDate(id, unavailability);
    }

    @GetMapping("/filter")
    @Operation(description = "Allows to get filtered unavailabilities.", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<List<UnavailabilityFilterDto>> getFiltered(UnavailabilitySearchCriteria unavailabilitySearchCriteria){
        return new ResponseEntity<>(unavailabilityService.findAllWithFilters(unavailabilitySearchCriteria),HttpStatus.OK);}

    @GetMapping("/monthly")
    @Operation(description = "Allows to get all unavailabilities for logged user company for given month.", security = @SecurityRequirement(name = "bearer-key"))
    public List<UnavailabilityToCalendarDto> getUnavabilities(@RequestParam int month, @RequestParam int year){
        return unavailabilityService.getAllForCompanyLoggedUserInMonth(month, year);
    }

}
