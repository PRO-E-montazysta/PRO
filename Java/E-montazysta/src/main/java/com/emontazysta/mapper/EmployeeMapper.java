package com.emontazysta.mapper;

import com.emontazysta.model.AppUser;
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
                .attachments(employee.getAttachments() == null ? null : employee.getAttachments().stream()
                        .map(attachment -> attachment.getId())
                        .collect(Collectors.toList()))
                .status(checkStatus(employee, LocalDateTime.now()) == null ? "AVAILABLE" : String.valueOf(checkStatus(employee, LocalDateTime.now()).getTypeOfUnavailability()))
                .unavailableFrom(checkStatus(employee, LocalDateTime.now()) == null ? null : checkStatus(employee, LocalDateTime.now()).getUnavailableFrom())
                .unavailableTo(checkStatus(employee, LocalDateTime.now()) == null ? null : checkStatus(employee, LocalDateTime.now()).getUnavailableTo())
                .unavailbilityDescription(checkStatus(employee, LocalDateTime.now()) == null ? null : checkStatus(employee, LocalDateTime.now()).getDescription())
                .build();
    }
}
