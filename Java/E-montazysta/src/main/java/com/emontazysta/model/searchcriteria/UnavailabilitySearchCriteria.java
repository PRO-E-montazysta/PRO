package com.emontazysta.model.searchcriteria;

import lombok.Data;

import java.util.List;

@Data
public class UnavailabilitySearchCriteria {

    private List<String> typeOfUnavailability;
    private String unavailableFrom;
    private String unavailableTo;
    private String assignedTo;
}
