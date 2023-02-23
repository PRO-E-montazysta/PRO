package com.emontazysta.model.dto;

import com.emontazysta.enums.TypeOfUnavailability;
import lombok.Builder;
import lombok.Data;
import java.util.Date;

@Data
@Builder
public class UnavailabilityDto {

    private Long id;
    private TypeOfUnavailability typeOfUnavailability;
    private String description;
    private Date unavailableFrom;
    private Date unavailableTo;
    private Long assignedToId;
    private Long assignedById;
}
