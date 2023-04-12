package com.emontazysta.model.dto;

import com.emontazysta.enums.TypeOfUnavailability;
import com.emontazysta.validation.IsAfter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnavailabilityWithLocalDateDto {

    private Long id;
    @NotNull(message = "Type of unavailability cannot be empty")
    private TypeOfUnavailability typeOfUnavailability;
    private String description;
    @NotNull(message = "Unavailable from cannot be empty")
    private LocalDate unavailableFrom;
    @NotNull(message = "Unavailable to cannot be empty")
    private LocalDate unavailableTo;
    @NotNull(message = "Assigned to id cannot be empty")
    private Long assignedToId;
    private Long assignedById;
}
