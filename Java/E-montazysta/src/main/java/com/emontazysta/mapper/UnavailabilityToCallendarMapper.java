package com.emontazysta.mapper;

import com.emontazysta.enums.TypeOfUnavailability;
import com.emontazysta.model.*;
import com.emontazysta.model.dto.UnavailabilityToCalendarDto;
import com.emontazysta.repository.FitterRepository;
import com.emontazysta.repository.LocationRepository;
import com.emontazysta.repository.OrderStageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UnavailabilityToCallendarMapper {

    private final OrderStageRepository orderStageRepository;
    private final FitterRepository fitterRepository;
    private final LocationRepository locationRepository;


    public UnavailabilityToCalendarDto toDto(Unavailability unavailability){
        return UnavailabilityToCalendarDto.builder()
                .id(unavailability.getId())
                .typeOfUnavailability(unavailability.getTypeOfUnavailability() == null ? null : unavailability.getTypeOfUnavailability())
                .description(unavailability.getDescription())
                .unavailableFrom(unavailability.getUnavailableFrom() == null ? null : unavailability.getUnavailableFrom())
                .unavailableTo(unavailability.getUnavailableTo() == null ? null : unavailability.getUnavailableTo())
                .assignedToId(unavailability.getAssignedTo().getId() == null ? null : unavailability.getAssignedTo().getId())
                .assignedToFirstName(unavailability.getAssignedTo().getFirstName() == null ? null : unavailability.getAssignedTo().getFirstName())
                .assignedToLastName(unavailability.getAssignedTo().getLastName() == null ? null : unavailability.getAssignedTo().getLastName())
                .assignedById(unavailability.getAssignedBy().getId() == null ? null :  unavailability.getAssignedBy().getId())
                .assignedByFirstName(unavailability.getAssignedBy().getFirstName() == null ? null : unavailability.getAssignedBy().getFirstName())
                .assignedByLastName(unavailability.getAssignedBy().getLastName() == null ? null : unavailability.getAssignedBy().getLastName())
                .orderStageId(findOrderStageId(unavailability) == null ? null : findOrderStageId(unavailability))
                .orderStageName(findOrderStageName(unavailability) == null ? null : findOrderStageName(unavailability))
                .companyId(findCompanyId(unavailability) == null ? null : findCompanyId(unavailability))
                .companyName(findCompanyName(unavailability) == null ? null : findCompanyName(unavailability))
                .orderId(findOrderId(unavailability) == null ? null : findOrderId(unavailability))
                .assignedFittersNumber(findAssignedFitterNumber(unavailability) == null ? null : findAssignedFitterNumber(unavailability))
                .plannedFittersNumber(findPlannedFittersNumber(unavailability) == null ? null : findPlannedFittersNumber(unavailability))
                .locationId(findLocationId(unavailability) == null ? null : findLocationId(unavailability))
                .city(findCity(unavailability) == null ? null : findCity(unavailability))
                .street(findStreet(unavailability) == null ? null : findStreet(unavailability))
                .propertyNumber(findPropertyNumber(unavailability) == null ? null : findPropertyNumber(unavailability))
                .apartmentNumber(findapartmentNumber(unavailability) == null ? null : findapartmentNumber(unavailability))
                .zipCode(findZipCode(unavailability) == null ? null : findZipCode(unavailability))
                .build();
    }

    private Location findLocation(Unavailability unavailability ) {
        OrderStage orderStage = findOrderStage(unavailability);
        if (orderStage != null) {
            return orderStage.getOrders().getLocation();
            }
        return null;
    }
    private Long findLocationId(Unavailability unavailability ) {
        OrderStage orderStage = findOrderStage(unavailability);
        Location location = findLocation(unavailability);
        if (orderStage != null && location != null) {
            return location.getId();
        }
        return null;
    }

    private String findCity(Unavailability unavailability ) {
        OrderStage orderStage = findOrderStage(unavailability);
        Location location = findLocation(unavailability);
        if (orderStage != null && location != null) {
            return location.getCity();
        }
        return null;
    }

    private String findStreet(Unavailability unavailability ) {
        OrderStage orderStage = findOrderStage(unavailability);
        Location location = findLocation(unavailability);
        if (orderStage != null && location != null) {
            return location.getStreet();
        }
        return null;
    }
    private String findPropertyNumber(Unavailability unavailability ) {
        OrderStage orderStage = findOrderStage(unavailability);
        Location location = findLocation(unavailability);
        if (orderStage != null && location != null) {
            return location.getPropertyNumber();
        }
        return null;
    }

    private String findapartmentNumber(Unavailability unavailability ) {
        OrderStage orderStage = findOrderStage(unavailability);
        Location location = findLocation(unavailability);
        if (orderStage != null && location != null) {
            return location.getApartmentNumber();
        }
        return null;
    }

    private String findZipCode(Unavailability unavailability ) {
        OrderStage orderStage = findOrderStage(unavailability);
        Location location = findLocation(unavailability);
        if (orderStage != null && location != null) {
            return location.getZipCode();
        }
        return null;
    }

    private Integer findPlannedFittersNumber(Unavailability unavailability ) {
        OrderStage orderStage = findOrderStage(unavailability);
        if (orderStage != null) {
            return orderStage.getPlannedFittersNumber();
            }
        return null;
    }

    private Integer findAssignedFitterNumber(Unavailability unavailability) {
        OrderStage orderStage = findOrderStage(unavailability);
        if (orderStage != null) {
            return orderStage.getAssignedTo().size();
            }
        return null;
    }

    private Long findOrderId(Unavailability unavailability) {
        OrderStage orderStage = findOrderStage(unavailability);
        if (orderStage != null) {
            return orderStage.getOrders().getId();
            }
        return null;
    }

    private Long findCompanyId(Unavailability unavailability) {
        OrderStage orderStage = findOrderStage(unavailability);
        if (orderStage != null) {
                 return orderStage.getOrders().getCompany().getId();
             }
        return null;
    }

    private String findCompanyName(Unavailability unavailability) {
        OrderStage orderStage = findOrderStage(unavailability);
        if (orderStage != null) {
            return orderStage.getOrders().getCompany().getCompanyName();
             }
        return null;
    }

    private Long findOrderStageId(Unavailability unavailability) {
        OrderStage orderStage = findOrderStage(unavailability);
        if (orderStage!= null) {
                return orderStage.getId();
            }
        return null;
    }

    private String findOrderStageName(Unavailability unavailability) {
        OrderStage orderStage = findOrderStage(unavailability);
        if (orderStage != null) {
                return orderStage.getName();
            }
        return null;
    }

    private OrderStage findOrderStage(Unavailability unavability){
        if (unavability.getTypeOfUnavailability().equals(TypeOfUnavailability.BUSY)) {
            Fitter fitter = fitterRepository.getReferenceById(unavability.getAssignedTo().getId());

            List<OrderStage> stages = orderStageRepository.findAllByPlannedStartDateEqualsAndPlannedEndDateEquals(unavability.getUnavailableFrom(), unavability.getUnavailableTo());

            for (OrderStage stage: stages) {
                return stage;

                }
            }
        return null;
    }


}
