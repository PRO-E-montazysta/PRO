package com.emontazysta.controller;

import com.emontazysta.model.dto.ManagerDto;
import com.emontazysta.service.ManagerService;
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
import java.util.List;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = API_BASE_CONSTANT + "/managers", produces = MediaType.APPLICATION_JSON_VALUE)
public class ManagerController {

    private final ManagerService managerService;

    @GetMapping("/all")
    @Operation(description = "Allows to get all Managers.", security = @SecurityRequirement(name = "bearer-key"))
    public List<ManagerDto> getAllManagers() {
        return managerService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(description = "Allows to get Manager by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public ManagerDto getManagerById(@PathVariable Long id) {
        return managerService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add new Manager.", security = @SecurityRequirement(name = "bearer-key"))
    public ManagerDto addManager(@Valid @RequestBody ManagerDto manager) {
        return managerService.add(manager);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Allows to delete Manager by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void deleteManagerById(@PathVariable Long id) {
        managerService.delete(id);
    }

    @PutMapping("/{id}")
    @Operation(description = "Allows to update Manager by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public ManagerDto updateManager(@PathVariable Long id, @Valid @RequestBody ManagerDto manager) {
        return managerService.update(id, manager);
    }
}
