package com.emontazysta.model.dto;

import com.emontazysta.validation.PlPhone;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.pl.PESEL;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
public class EmployeeDto extends AppUserDto {

    private Long id;
    @PlPhone
    private String phone;
    @PESEL(message = "PESEL is not valid")
    @NotBlank(message = "PESEL cannot be empty")
    private String pesel;
    private List<Long> unavailabilities;
    private List<Long> notifications;
    private List<Long> employeeComments;
    private List<Long> elementEvents;
    private List<Long> employments;
    private List<Long> attachments;
    private List<Long> toolEvents;
    private String status;
    private String unavailbilityDescription;
    private LocalDateTime unavailableFrom;
    private LocalDateTime unavailableTo;
}
