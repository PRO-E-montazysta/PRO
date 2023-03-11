package com.emontazysta.model.dto;

import com.emontazysta.enums.TypeOfUnavailability;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnavailabilityDto {

    private Long id;
    private TypeOfUnavailability typeOfUnavailability;
    private String description;
    private LocalDateTime unavailableFrom;
    private LocalDateTime unavailableTo;
    private Long assignedToId;
    private Long assignedById;
}
