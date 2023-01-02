package com.emontazysta.controller;

import com.emontazysta.mapper.ElementEventMapper;
import com.emontazysta.model.ElementEvent;
import com.emontazysta.model.dto.ElementEventDto;
import com.emontazysta.service.ElementEventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = API_BASE_CONSTANT + "/event", produces = MediaType.APPLICATION_JSON_VALUE)
public class ElementEventController {

    private final ElementEventService service;

    @GetMapping
    @Operation(description = "Allows to get list of all events", security= @SecurityRequirement(name = "bearer-key"))
    public List<ElementEventDto> getAllEvents() {
        return service.getAll().stream().map(ElementEventMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("{id}")
    @Operation(description = "Allows to get specific event by id", security = @SecurityRequirement(name= "bearer-key"))
    public ElementEventDto getEventById(Long id){
        return ElementEventMapper.toDto(service.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add event to element.", security = @SecurityRequirement(name= "bearer-key"))
    public void addElementEvent(@RequestBody ElementEvent event ){
        service.add(event);
    }

    @DeleteMapping("{id}")
    @Operation(description = "Allows to delete element event by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void deleteElementEventById(@PathVariable Long id) {
        service.delete(id);
    }

}
