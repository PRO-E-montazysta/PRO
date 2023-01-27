package com.emontazysta.model.dto;

import com.emontazysta.enums.TypeOfUnavailability;
import lombok.Builder;
import lombok.Data;
import java.util.Date;

@Builder
@Data
public class UnavailabilityDto {

    private Long id;
    private TypeOfUnavailability typeOfUnavailability;
    private String description;
    private Date unavailableFrom;
    private Date unavailableTo;
}
