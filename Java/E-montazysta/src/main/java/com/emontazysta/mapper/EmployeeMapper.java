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
                .status(checkStatus(employee, LocalDateTime.now() ))
                .build();
    }

    private String checkStatus(AppUser employee, LocalDateTime now) {
        List<Unavailability> unavailabilities = unavailabilityRepository.findAll();
        if (unavailabilities != null && !unavailabilities.isEmpty()) {
            for (Unavailability unavailability : unavailabilities) {
                if ((unavailability.getAssignedTo().getId() == employee.getId()) &&
                        (now.isAfter(unavailability.getUnavailableFrom()) && now.isBefore(unavailability.getUnavailableTo()))) {
                    return String.valueOf(unavailability.getTypeOfUnavailability());
                } else {
                    return "AVAIBLE";
                }
            }
        }
        return "AVAIBLE";
    }

}
