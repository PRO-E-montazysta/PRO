package com.emontazysta.mapper;

import com.emontazysta.model.AppUser;
import com.emontazysta.model.Employee;
import com.emontazysta.model.dto.EmployeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EployeeMapper {

    public EmployeeDto employeeToDto(AppUser employee){
       return EmployeeDto.builder()
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .roles(employee.getRoles())
                .phone(employee.getPhone())
                .attachments(employee.getAttachments().stream()
                        .map(attachment -> attachment.getId())
                        .collect(Collectors.toList()))
                .build();
    }

}
