package com.emontazysta.controller;

import com.emontazysta.mapper.FitterMapper;
import com.emontazysta.model.Fitter;
import com.emontazysta.model.dto.FitterDto;
import com.emontazysta.service.FitterService;
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
@RequestMapping(value = "/api/fitters", produces = MediaType.APPLICATION_JSON_VALUE)
public class FitterController {

    private final FitterService fitterService;

    @GetMapping
    public List<FitterDto> getAllFitters() {
        return fitterService.getAll().stream()
                .map(FitterMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public FitterDto getFitterById(@PathVariable Long id) {
        return FitterMapper.toDto(fitterService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addFitter(@RequestBody Fitter fitter) {
        fitterService.add(fitter);
    }

    @DeleteMapping("{id}")
    public void deleteFitterById(@PathVariable Long id) {
        fitterService.delete(id);
    }
}