package com.emontazysta.controller;

import com.emontazysta.mapper.SalesRepresentativeMapper;
import com.emontazysta.model.SalesRepresentative;
import com.emontazysta.model.dto.SalesRepresentativeDto;
import com.emontazysta.service.SalesRepresentativeService;
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
@RequestMapping(value = "/api/sales-representatives", produces = MediaType.APPLICATION_JSON_VALUE)
public class SalesRepresentativeController {

    private final SalesRepresentativeService salesRepresentativeService;

    @GetMapping
    public List<SalesRepresentativeDto> getAllSalesRepresentatives() {
        return salesRepresentativeService.getAll().stream()
                .map(SalesRepresentativeMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public SalesRepresentativeDto getSalesRepresentativeById(@PathVariable Long id) {
        return SalesRepresentativeMapper.toDto(salesRepresentativeService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addSalesRepresentative(@RequestBody SalesRepresentative salesRepresentative) {
        salesRepresentativeService.add(salesRepresentative);
    }

    @DeleteMapping("{id}")
    public void deleteSalesRepresentativeById(@PathVariable Long id) {
        salesRepresentativeService.delete(id);
    }
}
