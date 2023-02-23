package com.emontazysta.model.dto;

import com.emontazysta.enums.TypeOfUnavailability;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class UnavailabilityDto {

    private Long id;
    private TypeOfUnavailability typeOfUnavailability;
    private String description;
    private LocalDate unavailableFrom;
    private LocalDate unavailableTo;
    private Long assignedToId;
    private Long assignedById;
}
