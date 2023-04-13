package com.emontazysta.controller;

import com.emontazysta.model.dto.AboutMeDto;
import com.emontazysta.service.AboutMeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@AllArgsConstructor
@RequestMapping(value = API_BASE_CONSTANT + "/aboutme", produces = MediaType.APPLICATION_JSON_VALUE)
public class AboutMeController {

    private final AboutMeService aboutMeService;

    @GetMapping
    @Operation(description = "Allows to get information about logged user.", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<AboutMeDto> getInfoAboutMe() {
        return ResponseEntity.ok().body(aboutMeService.getInfoAboutMe());
    }
}
