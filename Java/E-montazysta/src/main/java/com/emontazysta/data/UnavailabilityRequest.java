package com.emontazysta.data;

import com.emontazysta.enums.TypeOfUnavailability;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class UnavailabilityRequest {

    private TypeOfUnavailability typeOfUnavailability;

    private String description;

    @JsonFormat(pattern="dd-MM-yyyy")
    private Date unavailableFrom;

    @JsonFormat(pattern="dd-MM-yyyy")
    private Date unavailableTo;
}
