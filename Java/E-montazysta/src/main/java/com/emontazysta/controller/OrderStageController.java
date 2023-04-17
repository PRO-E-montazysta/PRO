package com.emontazysta.controller;

import com.emontazysta.model.dto.OrderStageDto;
import com.emontazysta.model.dto.OrderStageWithToolsAndElementsDto;
import com.emontazysta.model.searchcriteria.OrdersStageSearchCriteria;
import com.emontazysta.service.OrderStageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import java.security.Principal;
import java.util.List;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = API_BASE_CONSTANT + "/order-stages", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderStageController {

    private final OrderStageService orderStageService;

    @GetMapping("/all")
    @Operation(description = "Allows to get all Order Stages.", security = @SecurityRequirement(name = "bearer-key"))
    public List<OrderStageDto> getAllOrderStages() {
        return orderStageService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(description = "Allows to get Order Stage by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public OrderStageDto getOrderStageById(@PathVariable Long id) {
        return orderStageService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add new Order Stage.", security = @SecurityRequirement(name = "bearer-key"))
    public OrderStageDto addOrderStage(@Valid @RequestBody OrderStageWithToolsAndElementsDto orderStage) {
        return orderStageService.addWithToolsAndElements(orderStage);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Allows to delete Order Stage by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void deleteOrderStageById(@PathVariable Long id) {
        orderStageService.delete(id);
    }

    @PutMapping("/{id}")
    @Operation(description = "Allows to update Order Stage by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public OrderStageDto updateOrderStage(@PathVariable Long id, @Valid @RequestBody OrderStageWithToolsAndElementsDto orderStage) {
        return orderStageService.update(id, orderStage);
    }

    @GetMapping("/filter")
    @Operation(description = "Return filtered OrdersStage by given parameters.", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<List<OrderStageDto>> Stages(OrdersStageSearchCriteria ordersStageSearchCriteria, Principal principal){
        return new ResponseEntity<>(orderStageService.getFilteredOrders(ordersStageSearchCriteria, principal), HttpStatus.OK);
    }
}
