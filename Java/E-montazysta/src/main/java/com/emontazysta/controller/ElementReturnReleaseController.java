package com.emontazysta.controller;

import com.emontazysta.mapper.ElementReturnReleaseMapper;
import com.emontazysta.model.ElementReturnRelease;
import com.emontazysta.model.dto.ElementReturnReleaseDto;
import com.emontazysta.service.ElementReturnReleaseService;
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
import java.util.stream.Collectors;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = API_BASE_CONSTANT + "/elementReturnReleases", produces = MediaType.APPLICATION_JSON_VALUE)
public class ElementReturnReleaseController {

    private final ElementReturnReleaseService elementReturnReleaseService;

    @GetMapping("/all")
    @Operation(description = "Allows to get all Element Return Releases.", security = @SecurityRequirement(name = "bearer-key"))
    public List<ElementReturnReleaseDto> getAllElementReturnReleases() {
        return elementReturnReleaseService.getAll().stream()
                .map(ElementReturnReleaseMapper::elementReturnReleaseToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(description = "Allows to get Element Return Release by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public ElementReturnReleaseDto getElementReturnReleaseById(@PathVariable Long id) {
        return ElementReturnReleaseMapper.elementReturnReleaseToDto(elementReturnReleaseService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add new Element Return Release.", security = @SecurityRequirement(name = "bearer-key"))
    public void addElementReturnRelease(@Valid @RequestBody ElementReturnRelease elementReturnRelease) {
        elementReturnReleaseService.add(elementReturnRelease);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Allows to delete Element Return Release by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void deleteElementReturnReleaseById(@PathVariable Long id) {
        elementReturnReleaseService.delete(id);
    }

    @PutMapping("/{id}")
    @Operation(description = "Allows to update Element Return Release by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public ElementReturnRelease updateElementReturnRelease(@PathVariable Long id, @Valid @RequestBody ElementReturnRelease elementReturnRelease) {
        return elementReturnReleaseService.update(id, elementReturnRelease);
    }
}
