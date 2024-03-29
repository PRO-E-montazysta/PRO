package com.emontazysta.controller;

import com.emontazysta.model.dto.OrdersCompanyManagerDto;
import com.emontazysta.model.dto.OrdersDto;

import com.emontazysta.model.searchcriteria.OrdersSearchCriteria;
import com.emontazysta.service.impl.OrdersServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = API_BASE_CONSTANT + "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrdersController {

    private final OrdersServiceImpl orderService;

    @GetMapping("/all")
    @Operation(description = "Allows to get all Orders.", security = @SecurityRequirement(name = "bearer-key"))
    public List<OrdersDto> getAll() {
        return orderService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(description = "Allows to get Order by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public OrdersDto getById(@PathVariable Long id) {
        return orderService.getById(id);
    }


    @PreAuthorize("hasAuthority('SCOPE_SALES_REPRESENTATIVE')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add new Order.", security = @SecurityRequirement(name = "bearer-key"))
    public OrdersDto add(@Valid @RequestBody OrdersDto orders, Principal principal) {
        OrdersDto ordersDtoWithCompanyId = orderService.findPrincipalCompanyId(orders, principal);
        return orderService.add(ordersDtoWithCompanyId);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Allows to delete Order by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void deleteById(@PathVariable Long id) {
        orderService.delete(id);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_MANAGER','SCOPE_SALES_REPRESENTATIVE')")
    @PutMapping("/{id}")
    @Operation(description = "Allows to edit Order by given Id and Order data.", security = @SecurityRequirement(name = "bearer-key"))
    public OrdersDto update(@PathVariable Long id, @Valid @RequestBody OrdersDto orders) {
        return orderService.update(id, orders);
    }

    @GetMapping("/filter")
    @Operation(description = "Return filtered Orders by given parameters.", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<List<OrdersCompanyManagerDto>> filterOrders(OrdersSearchCriteria ordersSearchCriteria, Principal principal){
        return new ResponseEntity<>(orderService.getFilteredOrders(ordersSearchCriteria, principal), HttpStatus.OK);
    }

    @PutMapping("/nextStatus/{id}")
    @Operation(description = "Allows to change OrderStatus to next one.", security = @SecurityRequirement(name = "bearer-key"))
    public OrdersDto nextStatus(@PathVariable Long id) {
        return orderService.nextStatus(id);
    }

    @PutMapping("/previousStatus/{id}")
    @Operation(description = "Allows to change OrderStatus to previous one.", security = @SecurityRequirement(name = "bearer-key"))
    public OrdersDto previousStatus(@PathVariable Long id) {
        return orderService.previousStatus(id);
    }
}
