package com.emontazysta.controller;

import com.emontazysta.mapper.SpecialistMapper;
import com.emontazysta.model.Specialist;
import com.emontazysta.model.dto.SpecialistDto;
import com.emontazysta.service.SpecialistService;
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

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/specialists", produces = MediaType.APPLICATION_JSON_VALUE)
public class SpecialistController {

    private final SpecialistService specialistService;

    @GetMapping
    public List<SpecialistDto> getAllSpecialists() {
        return specialistService.getAll().stream()
                .map(SpecialistMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public SpecialistDto getSpecialistById(@PathVariable Long id) {
        return SpecialistMapper.toDto(specialistService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addSpecialist(@RequestBody Specialist specialist) {
        specialistService.add(specialist);
    }

    @DeleteMapping("{id}")
    public void deleteSpecialistById(@PathVariable Long id) {
        specialistService.delete(id);
    }
}
