package com.emontazysta.controller;

import com.emontazysta.mapper.ManagerMapper;
import com.emontazysta.model.Manager;
import com.emontazysta.model.dto.ManagerDto;
import com.emontazysta.service.ManagerService;
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
@RequestMapping(value = "/api/managers", produces = MediaType.APPLICATION_JSON_VALUE)
public class ManagerController {

    private final ManagerService managerService;

    @GetMapping
    public List<ManagerDto> getAllManagers() {
        return managerService.getAll().stream()
                .map(ManagerMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public ManagerDto getManagerById(@PathVariable Long id) {
        return ManagerMapper.toDto(managerService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addManager(@RequestBody Manager manager) {
        managerService.add(manager);
    }

    @DeleteMapping("{id}")
    public void deleteManagerById(@PathVariable Long id) {
        managerService.delete(id);
    }
}
