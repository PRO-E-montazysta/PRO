package com.emontazysta.controller;

import com.emontazysta.mapper.ClientMapper;
import com.emontazysta.model.Client;
import com.emontazysta.model.dto.ClientDto;
import com.emontazysta.service.impl.ClientServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = API_BASE_CONSTANT + "/clients", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientController {

    private final ClientServiceImpl clientService;

    @GetMapping("/all")
    @Operation(description = "Allows to get all Clients.", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity <List<ClientDto>> getAll() {
        return ResponseEntity.ok().body(clientService.getAll().stream()
                .map(ClientMapper::clientToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(description = "Allows to get Client by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public ClientDto getById(@PathVariable Long id) {
        return ClientMapper.clientToDto(clientService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add new Client.", security = @SecurityRequirement(name = "bearer-key"))
    public void add(@Valid @RequestBody Client client) {
        clientService.add(client);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Allows to delete Client by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void deleteById(@PathVariable Long id) {
        clientService.delete(id);
    }

    @PutMapping("/{id}")
    @Operation(description = "Allows to update Client by given Id and Client.", security = @SecurityRequirement(name = "bearer-key"))
    public void update(@PathVariable Long id, @RequestBody Client client) {
        clientService.update(id, client);
    }
}
