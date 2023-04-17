package com.emontazysta.controller;

import com.emontazysta.model.dto.ElementsPlannedNumberDto;
import com.emontazysta.service.ElementsPlannedNumberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = API_BASE_CONSTANT + "/elemnts-planned", produces = MediaType.APPLICATION_JSON_VALUE)
public class ElementsPlannedNumberController {

    private final ElementsPlannedNumberService elementsPlannedNumberService;

    @GetMapping("/all")
    @Operation(description = "Allows to geta all planed number of elements.", security = @SecurityRequirement(name = "bearer-key"))
    public List<ElementsPlannedNumberDto> getAllElementsPlannedNumber() {
        return elementsPlannedNumberService.getAll();
    }

    @GetMapping("{id}")
    @Operation(description = "Allows to get planed number of tools by id.", security = @SecurityRequirement(name = "bearer-key"))
    public ElementsPlannedNumberDto getElemwntsPlannedNumberById(@PathVariable Long id) {
        return elementsPlannedNumberService.getById(id);
    }
}
