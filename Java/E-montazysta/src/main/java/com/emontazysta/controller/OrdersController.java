package com.emontazysta.controller;

import com.emontazysta.mapper.OrdersMapper;
import com.emontazysta.model.Orders;
import com.emontazysta.model.dto.OrdersDto;
import com.emontazysta.service.impl.OrdersServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = API_BASE_CONSTANT + "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrdersController {

    private final OrdersServiceImpl orderService;

    @GetMapping("/all")
    @Operation(description = "Allows to get all Orders.", security = @SecurityRequirement(name = "bearer-key"))
    public List<OrdersDto> getAll() {
        return orderService.getAll().stream()
                .map(OrdersMapper::ordersToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(description = "Allows to get Order by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public OrdersDto getById(@PathVariable Long id) {
        return OrdersMapper.ordersToDto(orderService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add new Order.", security = @SecurityRequirement(name = "bearer-key"))
    public void add(@Valid @RequestBody Orders orders) {
        orderService.add(orders);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Allows to delete Order by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void deleteById(@PathVariable Long id) {
        orderService.delete(id);
    }

    @PutMapping("/{id}")
    @Operation(description = "Allows to edit Order by given Id and Order data.", security = @SecurityRequirement(name = "bearer-key"))
    public void update(@PathVariable Long id, @Valid @RequestBody Orders orders) {
        orderService.update(id, orders);
    }
}
