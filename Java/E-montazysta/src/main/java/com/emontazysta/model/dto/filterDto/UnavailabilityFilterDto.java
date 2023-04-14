package com.emontazysta.model.dto.filterDto;

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
public class UnavailabilityFilterDto {

    private Long id;
    private TypeOfUnavailability typeOfUnavailability;
    private LocalDateTime unavailableFrom;
    private LocalDateTime unavailableTo;
    private String assignedTo;
}
