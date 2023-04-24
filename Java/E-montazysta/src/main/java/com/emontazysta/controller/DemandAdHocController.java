package com.emontazysta.controller;

import com.emontazysta.model.dto.ClientDto;
import com.emontazysta.model.dto.DemandAdHocDto;
import com.emontazysta.model.dto.filterDto.DemandAdHocFilterDto;
import com.emontazysta.model.searchcriteria.ClientSearchCriteria;
import com.emontazysta.model.searchcriteria.DemandAdHocSearchCriteria;
import com.emontazysta.service.DemandAdHocService;
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
@RequestMapping(value = API_BASE_CONSTANT + "/demands-adhoc", produces = MediaType.APPLICATION_JSON_VALUE)
public class DemandAdHocController {

    private final DemandAdHocService demandAdHocService;

    @GetMapping("/all")
    @Operation(description = "Allows to get all Demands Ad Hoc.", security = @SecurityRequirement(name = "bearer-key"))
    public List<DemandAdHocDto> getAllDemandsAdHoc() {
        return demandAdHocService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(description = "Allows to get Demand Ad Hoc by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public DemandAdHocDto getDemandAdHocById(@PathVariable Long id) {
        return demandAdHocService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add new Demand Ad Hoc.", security = @SecurityRequirement(name = "bearer-key"))
    public DemandAdHocDto addDemandAdHoc(@Valid @RequestBody DemandAdHocDto demandAdHoc) {
        return demandAdHocService.add(demandAdHoc);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Allows to delete Demand Ad Hoc by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void deleteDemandAdHocById(@PathVariable Long id) {
        demandAdHocService.delete(id);
    }

    @PutMapping("/{id}")
    @Operation(description = "Allows to edit Demand Ad Hoc by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public DemandAdHocDto updateDemandAdHoc(@PathVariable Long id, @Valid @RequestBody DemandAdHocDto demandAdHoc) {
        return demandAdHocService.update(id, demandAdHoc);
    }

    @GetMapping("/filter")
    @Operation(description = "Return filtered Orders by given parameters.", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<List<DemandAdHocFilterDto>> filter(DemandAdHocSearchCriteria demandAdHocSearchCriteria){
        return new ResponseEntity<>(demandAdHocService.getFiltered(demandAdHocSearchCriteria), HttpStatus.OK);
    }
}
