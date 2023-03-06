package com.emontazysta.mapper;

import com.emontazysta.model.AppUser;
import com.emontazysta.model.Unavailability;
import com.emontazysta.model.dto.EmployeeDto;
import com.emontazysta.repository.UnavailabilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EmployeeMapper {

    private final UnavailabilityRepository unavailabilityRepository;

    public EmployeeDto toDto(AppUser employee){
       return EmployeeDto.builder()
               .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .roles(employee.getRoles())
                .phone(employee.getPhone())
                .attachments( employee.getAttachments() == null ? null :employee.getAttachments().stream()
                        .map(attachment -> attachment.getId())
                        .collect(Collectors.toList()))
                .status(checkStatus(employee, LocalDateTime.now()) == null ? "AVAIBLE" : String.valueOf(checkStatus(employee, LocalDateTime.now()).getTypeOfUnavailability()))
                .unavailableFrom(checkStatus(employee, LocalDateTime.now()) == null ? null : checkStatus(employee, LocalDateTime.now()).getUnavailableFrom())
                .unavailableTo(checkStatus(employee, LocalDateTime.now()) == null ? null : checkStatus(employee, LocalDateTime.now()).getUnavailableTo())
                .unavailbilityDescription(checkStatus(employee, LocalDateTime.now()) == null ? null : checkStatus(employee, LocalDateTime.now()).getDescription())
                .build();
    }

    private Unavailability checkStatus(AppUser employee, LocalDateTime now) {
        List<Unavailability> unavailabilities = unavailabilityRepository.findAll();
        if (unavailabilities != null && !unavailabilities.isEmpty()) {
            for (Unavailability unavailability : unavailabilities) {
                if ((unavailability.getAssignedTo().getId() == employee.getId()) &&
                        (now.isAfter(unavailability.getUnavailableFrom()) && now.isBefore(unavailability.getUnavailableTo()))) {
                    return unavailability;
                }
            }
        }
        return null;
    }
}
