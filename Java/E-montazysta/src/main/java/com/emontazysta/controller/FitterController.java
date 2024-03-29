package com.emontazysta.controller;

import com.emontazysta.model.dto.FitterDto;
import com.emontazysta.model.dto.WorkingOnDto;
import com.emontazysta.model.searchcriteria.AppUserSearchCriteria;
import com.emontazysta.service.FitterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = API_BASE_CONSTANT + "/fitters", produces = MediaType.APPLICATION_JSON_VALUE)
public class FitterController {

    private final FitterService fitterService;

    @GetMapping("/all")
    @Operation(description = "Allows to get all Fitters.", security = @SecurityRequirement(name = "bearer-key"))
    public List<FitterDto> getAllFitters(Principal principal) {
        return fitterService.getAll(principal);
    }

    @GetMapping("/{id}")
    @Operation(description = "Allows to get Fitter by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public FitterDto getFitterById(@PathVariable Long id) {
        return fitterService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add new Fitter.", security = @SecurityRequirement(name = "bearer-key"))
    public FitterDto addFitter(@Valid @RequestBody FitterDto fitter) {
        return fitterService.add(fitter);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Allows to delete Fitter by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void deleteFitterById(@PathVariable Long id) {
        fitterService.delete(id);
    }

    @PutMapping("/{id}")
    @Operation(description = "Allows to update Fitter by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public FitterDto updateFitter(@PathVariable Long id, @Valid @RequestBody FitterDto fitter) {
        return fitterService.update(id, fitter);
    }

    @GetMapping("/available/{availableFrom}/{availableTo}")
    @Operation(description = "Allows to get available Fitters.", security = @SecurityRequirement(name = "bearer-key"))
    public List<FitterDto> getAvailableFitters(@PathVariable String availableFrom, @PathVariable String availableTo, Principal principal) {
        AppUserSearchCriteria appUserSearchCriteria = new AppUserSearchCriteria();
        appUserSearchCriteria.setAvailableFrom(availableFrom);
        appUserSearchCriteria.setAvailableTo(availableTo);
        return fitterService.getAvailable(appUserSearchCriteria, principal);
    }

    @GetMapping("/work-history/{id}")
    @Operation(description = "Allows to get work history.", security = @SecurityRequirement(name = "bearer-key"))
    public List<WorkingOnDto> getWorkingHistory(@PathVariable Long id) {
        return fitterService.getWorkingOn(id);
    }
}
