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
<<<<<<< HEAD
    @NotBlank(message = "Phone cannot be empty")
    @Pattern(regexp ="^\\+?[0-9]{10,}$|^\\+?[0-9]{1,3}[-\\s()]*[0-9]{6,}$", message = "Phone number has to be valid") // TODO custom annotation
=======
    @PlPhone
>>>>>>> master
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
