package com.emontazysta.mapper;

import com.emontazysta.model.Unavailability;
import com.emontazysta.model.dto.UnavailabilityDto;

public class UnavailabilityMapper {

    public static UnavailabilityDto unavailabilityToDto (Unavailability unavailability) {
        return UnavailabilityDto.builder()
                .id(unavailability.getId())
                .typeOfUnavailability(unavailability.getTypeOfUnavailability())
                .description(unavailability.getDescription())
                .unavailableFrom(unavailability.getUnavailableFrom())
                .unavailableTo(unavailability.getUnavailableTo())
                .build();
    }
}
