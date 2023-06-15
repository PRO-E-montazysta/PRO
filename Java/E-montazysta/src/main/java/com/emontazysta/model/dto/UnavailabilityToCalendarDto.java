package com.emontazysta.model.dto;

import com.emontazysta.enums.TypeOfUnavailability;
import com.emontazysta.validation.IsAfter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IsAfter(startDateFieldName = "unavailableFrom", endDateFieldName = "unavailableTo")
public class UnavailabilityToCalendarDto {

    private Long id;
    @NotNull(message = "Type of unavailability cannot be empty")
    private TypeOfUnavailability typeOfUnavailability;
    private String description;
    @NotNull(message = "Unavailable from cannot be empty")
    private LocalDateTime unavailableFrom;
    @NotNull(message = "Unavailable to cannot be empty")
    private LocalDateTime unavailableTo;
    @NotNull(message = "Assigned to id cannot be empty")
    private Long assignedToId;
    private String assignedToFirstName;
    private String assignedToLastName;
    private Long assignedById;
    private String assignedByFirstName;
    private String assignedByLastName;
    private Long orderStageId;
    private String orderStageName;
    private Long orderId;
    private Long companyId;
    private String companyName;
    private Integer assignedFittersNumber;
    private Integer plannedFittersNumber;
    private Long locationId;
    private String city;
    private String street;
    private String propertyNumber;
    private String apartmentNumber;
    private String zipCode;
}
