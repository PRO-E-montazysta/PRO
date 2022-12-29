package com.emontazysta.controller;

import com.emontazysta.mapper.FitterMapper;
import com.emontazysta.model.Fitter;
import com.emontazysta.model.OrderStage;
import com.emontazysta.model.dto.FitterDto;
import com.emontazysta.service.OrderStageService;
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
import java.util.stream.Collectors;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = API_BASE_CONSTANT + "/order-stages", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderStageController {

    private final OrderStageService orderStageService;

    @GetMapping
    @Operation(description = "Allows to get all Order Stages.", security = @SecurityRequirement(name = "bearer-key"))
    public List<OrderStage> getAllOrderStages() {
        return orderStageService.getAll();
    }

    @GetMapping("{id}")
    @Operation(description = "Allows to get Order Stage by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public OrderStage getOrderStageById(@PathVariable Long id) {
        return orderStageService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add new Order Stage.", security = @SecurityRequirement(name = "bearer-key"))
    public void addOrderStage(@RequestBody OrderStage orderStage) {
        orderStageService.add(orderStage);
    }

    @DeleteMapping("{id}")
    @Operation(description = "Allows to delete Order Stage by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void deleteOrderStageById(@PathVariable Long id) {
        orderStageService.delete(id);
    }
}
