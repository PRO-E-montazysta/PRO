package com.emontazysta.model.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
public class EmployeeDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phone;
    private String pesel;
    private List<Long> unavailabilities;
    private List<Long> notifications;
    private List<Long> employeeComments;
    private List<Long> elementEvents;
    private List<Long> employments;
    private List<Long> attachments;
    private List<Long> toolEvents;
}
