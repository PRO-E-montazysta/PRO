package com.emontazysta.controller;

import com.emontazysta.model.dto.filterDto.EventFilterDto;
import com.emontazysta.model.searchcriteria.EventSearchCriteria;
import com.emontazysta.repository.criteria.EventCriteriaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
@RequestMapping(value = API_BASE_CONSTANT + "/events", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventController {

    private final EventCriteriaRepository eventCriteriaRepository;

    @GetMapping("/filter")
    @Operation(description = "Return filtered Orders by given parameters.", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<List<EventFilterDto>> filterEvents(EventSearchCriteria eventSearchCriteria, Principal principal){
        return new ResponseEntity<>(eventCriteriaRepository.findAllWithFilters(eventSearchCriteria, principal), HttpStatus.OK);
    }
}
