package com.emontazysta.controller;

import com.emontazysta.service.impl.RoleServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = API_BASE_CONSTANT+"/roles", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoleController {

    private final RoleServiceImpl roleService;

    @GetMapping("/all")
    @Operation(description = "Allows to get all Roles.", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<List<String>> getAllRoles(Principal principal) {
            return ResponseEntity.ok().body(roleService.getAllRoles(principal));
    }
}
