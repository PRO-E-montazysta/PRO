package com.emontazysta.controller;

import com.emontazysta.model.ElementReturnRelease;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = API_BASE_CONSTANT + "/element-return-releases", produces = MediaType.APPLICATION_JSON_VALUE)
public class ElementReturnReleaseController {

    private final ElementReturnReleaseService elementReturnReleaseService;

    @GetMapping
    @Operation(description = "Allows to get all Element Return Releases.", security = @SecurityRequirement(name = "bearer-key"))
    public List<ElementReturnRelease> getAllElementReturnReleases() {
        return elementReturnReleaseService.getAll();
    }

    @GetMapping("{id}")
    @Operation(description = "Allows to get Element Return Release by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public ElementReturnRelease getElementReturnReleaseById(@PathVariable Long id) {
        return elementReturnReleaseService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add new Element Return Release.", security = @SecurityRequirement(name = "bearer-key"))
    public void addElementReturnRelease(@RequestBody ElementReturnRelease elementReturnRelease) {
        elementReturnReleaseService.add(elementReturnRelease);
    }

    @DeleteMapping("{id}")
    @Operation(description = "Allows to delete Element Return Release by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void deleteElementReturnReleaseById(@PathVariable Long id) {
        elementReturnReleaseService.delete(id);
    }
}
