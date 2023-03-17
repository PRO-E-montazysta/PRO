package com.emontazysta.controller;

import com.emontazysta.model.dto.ClientDto;
import com.emontazysta.model.searchcriteria.ClientSearchCriteria;
import com.emontazysta.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = API_BASE_CONSTANT + "/clients", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/all")
    @Operation(description = "Allows to get all Clients.", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity <List<ClientDto>> getAll() {
        return ResponseEntity.ok().body(clientService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(description = "Allows to get Client by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<ClientDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(clientService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add new Client.", security = @SecurityRequirement(name = "bearer-key"))
    public ClientDto add(@Valid @RequestBody ClientDto clientDto) {
        return clientService.add(clientDto);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Allows to delete Client by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void deleteById(@PathVariable Long id) {
        clientService.delete(id);
    }

    @PutMapping("/{id}")
    @Operation(description = "Allows to update Client by given Id and Client.", security = @SecurityRequirement(name = "bearer-key"))
    public ClientDto update(@PathVariable Long id, @Valid @RequestBody ClientDto clientDto) {
        return clientService.update(id, clientDto);
    }

    @GetMapping("/filter")
    @Operation(description = "Return filtered Orders by given parameters.", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<List<ClientDto>> filterClients(ClientSearchCriteria clientSearchCriteria, Principal principal){
        return new ResponseEntity<>(clientService.getFilteredOrders(clientSearchCriteria, principal), HttpStatus.OK);
    }
}
