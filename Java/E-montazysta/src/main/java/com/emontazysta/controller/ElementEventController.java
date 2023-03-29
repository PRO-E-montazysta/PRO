package com.emontazysta.controller;

import com.emontazysta.model.dto.ElementEventDto;
import com.emontazysta.service.ElementEventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = API_BASE_CONSTANT + "/elementEvent", produces = MediaType.APPLICATION_JSON_VALUE)
public class ElementEventController {

    private final ElementEventService service;

    //TO_DELETE
    @GetMapping("/all")
    @Operation(description = "Allows to get list of all events", security= @SecurityRequirement(name = "bearer-key"))
    public List<ElementEventDto> getAllEvents() {
        return service.getAll();
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_FITTER', 'SCOPE_FOREMAN', 'SCOPE_WAREHOUSE_MAN', 'SCOPE_WAREHOUSE_MANAGER', 'SCOPE_MANAGER')")
    @GetMapping("/{id}")
    @Operation(description = "Allows to get specific event by id", security = @SecurityRequirement(name= "bearer-key"))
    public ElementEventDto getEventById(@PathVariable Long id){
        return service.getById(id);
    }


    @PreAuthorize("hasAnyAuthority('SCOPE_FITTER', 'SCOPE_FOREMAN', 'SCOPE_WAREHOUSE_MAN', 'SCOPE_WAREHOUSE_MANAGER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add event to element.", security = @SecurityRequirement(name= "bearer-key"))
    public ElementEventDto addElementEvent(@Valid @RequestBody ElementEventDto event ){
        return service.add(event);
    }

    //TO_DELETE
    @DeleteMapping("/{id}")
    @Operation(description = "Allows to delete element event by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void deleteElementEventById(@PathVariable Long id) {
        service.delete(id);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_FITTER', 'SCOPE_FOREMAN', 'SCOPE_WAREHOUSE_MAN', 'SCOPE_WAREHOUSE_MANAGER', 'SCOPE_MANAGER')")
    @PutMapping("/{id}")
    @Operation(description = "Allows to update element event by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public ElementEventDto update(@PathVariable Long id, @Valid @RequestBody ElementEventDto event) {
        return service.update(id, event);
    }

}
