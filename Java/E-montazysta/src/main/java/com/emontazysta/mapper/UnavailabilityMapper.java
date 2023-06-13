package com.emontazysta.mapper;

import com.emontazysta.model.Unavailability;
import com.emontazysta.model.dto.UnavailabilityDto;
import com.emontazysta.model.dto.filterDto.UnavailabilityFilterDto;
import com.emontazysta.repository.AppUserRepository;
import com.emontazysta.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
@RequiredArgsConstructor
public class UnavailabilityMapper {

    private final AppUserRepository appUserRepository;
    private final ManagerRepository managerRepository;

    public UnavailabilityDto toDto(Unavailability unavailability) {
        return UnavailabilityDto.builder()
                .id(unavailability.getId())
                .typeOfUnavailability(unavailability.getTypeOfUnavailability())
                .description(unavailability.getDescription())
                .unavailableFrom(unavailability.getUnavailableFrom())
                .unavailableTo(unavailability.getUnavailableTo())
                .assignedToId(unavailability.getAssignedTo() == null ? null : unavailability.getAssignedTo().getId())
                .assignedById(unavailability.getAssignedBy() == null ? null : unavailability.getAssignedBy().getId())
                .orderStageId(unavailability.getOrderStageId() == null ? null : unavailability.getOrderStageId())
                .build();
    }

    public Unavailability toEntity(UnavailabilityDto unavailabilityDto) {
        return Unavailability.builder()
                .id(unavailabilityDto.getId())
                .typeOfUnavailability(unavailabilityDto.getTypeOfUnavailability())
                .description(unavailabilityDto.getDescription())
                .unavailableFrom(unavailabilityDto.getUnavailableFrom())
                .unavailableTo(unavailabilityDto.getUnavailableTo())
                .assignedTo(unavailabilityDto.getAssignedToId() == null ? null : appUserRepository.findById(unavailabilityDto.getAssignedToId()).orElseThrow(EntityNotFoundException::new))
                .assignedBy(unavailabilityDto.getAssignedById() == null ? null : managerRepository.findById(unavailabilityDto.getAssignedById()).orElseThrow(EntityNotFoundException::new))
                .orderStageId(unavailabilityDto.getOrderStageId() == null ? null : unavailabilityDto.getOrderStageId())
                .build();
    }

    public UnavailabilityFilterDto toFilterDto(Unavailability unavailability) {
        return UnavailabilityFilterDto.builder()
                .id(unavailability.getId())
                .typeOfUnavailability(unavailability.getTypeOfUnavailability())
                .unavailableFrom(unavailability.getUnavailableFrom())
                .unavailableTo(unavailability.getUnavailableTo())
                .assignedTo(unavailability.getAssignedTo().getFirstName() + " "
                        + unavailability.getAssignedTo().getLastName())
                .build();
    }
}
