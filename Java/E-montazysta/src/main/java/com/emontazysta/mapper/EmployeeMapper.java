package com.emontazysta.mapper;

import com.emontazysta.model.AppUser;
import com.emontazysta.model.Attachment;
import com.emontazysta.model.Unavailability;
import com.emontazysta.model.dto.EmployeeDto;
import com.emontazysta.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EmployeeMapper {

    private final StatusService statusService;

    public EmployeeDto toDto(AppUser employee) {
        return EmployeeDto.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .roles(employee.getRoles())
                .phone(employee.getPhone())
                .attachments( employee.getAttachments() == null ? null : employee.getAttachments().stream()
                        .map(Attachment::getId)
                        .collect(Collectors.toList()))
                .status(statusService.checkUnavailability(employee) == null ? "AVAILABLE" : String.valueOf(statusService.checkUnavailability(employee).getTypeOfUnavailability()))
                .unavailableFrom(statusService.checkUnavailability(employee) == null ? null : statusService.checkUnavailability(employee).getUnavailableFrom())
                .unavailableTo(statusService.checkUnavailability(employee) == null ? null : statusService.checkUnavailability(employee).getUnavailableTo())
                .unavailbilityDescription(statusService.checkUnavailability(employee) == null ? null : statusService.checkUnavailability(employee).getDescription())
                .deleted(employee.isDeleted())
                .build();
    }
}
