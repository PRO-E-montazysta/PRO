package com.emontazysta.controller;

import com.emontazysta.model.dto.ForemanDto;
import com.emontazysta.model.dto.WorkingOnDto;
import com.emontazysta.service.ForemanService;
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
@RequestMapping(value = API_BASE_CONSTANT + "/foremen", produces = MediaType.APPLICATION_JSON_VALUE)
public class ForemanController {

    private final ForemanService foremanService;

    @GetMapping("/all")
    @Operation(description = "Allows to get all Foremen.", security = @SecurityRequirement(name = "bearer-key"))
    public List<ForemanDto> getAllForemen(Principal principal) {
        return foremanService.getAll(principal);
    }

    @GetMapping("/{id}")
    @Operation(description = "Allows to get Foreman by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public ForemanDto getForemanById(@PathVariable Long id) {
        return foremanService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add new Foreman.", security = @SecurityRequirement(name = "bearer-key"))
    public ForemanDto addForeman(@Valid @RequestBody ForemanDto foreman) {
        return foremanService.add(foreman);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Allows to delete Foreman by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void deleteForemanById(@PathVariable Long id) {
        foremanService.delete(id);
    }

    @PutMapping("/{id}")
    @Operation(description = "Allows to update Foreman by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public ForemanDto updateForeman(@PathVariable Long id, @Valid @RequestBody ForemanDto foreman) {
        return foremanService.update(id, foreman);
    }

    @GetMapping("/work-history/{id}")
    @Operation(description = "Allows to get work history.", security = @SecurityRequirement(name = "bearer-key"))
    public List<WorkingOnDto> getWorkingHistory(@PathVariable Long id) {
        return foremanService.getWorkingOn(id);
    }
}
