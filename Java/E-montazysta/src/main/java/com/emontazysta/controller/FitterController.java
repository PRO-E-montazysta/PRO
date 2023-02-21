package com.emontazysta.controller;

import com.emontazysta.mapper.FitterMapper;
import com.emontazysta.model.Fitter;
import com.emontazysta.model.dto.FitterDto;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = API_BASE_CONSTANT + "/fitters", produces = MediaType.APPLICATION_JSON_VALUE)
public class FitterController {

    private final FitterService fitterService;

    @GetMapping("/all")
    @Operation(description = "Allows to get all Fitters.", security = @SecurityRequirement(name = "bearer-key"))
    public List<FitterDto> getAllFitters() {
        return fitterService.getAll().stream()
                .map(FitterMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(description = "Allows to get Fitter by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public FitterDto getFitterById(@PathVariable Long id) {
        return FitterMapper.toDto(fitterService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add new Fitter.", security = @SecurityRequirement(name = "bearer-key"))
    public void addFitter(@Valid @RequestBody Fitter fitter) {
        fitterService.add(fitter);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Allows to delete Fitter by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void deleteFitterById(@PathVariable Long id) {
        fitterService.delete(id);
    }
}