package com.emontazysta.model.dto;

import com.emontazysta.enums.CompanyStatus;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.pl.PESEL;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyWithAdminDto {


    @NotBlank(message = "Company name cannot be empty")
    private String companyName;
    @NotNull(message = "Status cannot be empty")
    private CompanyStatus status;
    private String statusReason;


    @NotBlank(message = "First name cannot be empty")
    @Length(min = 3, max = 32, message = "First name has to be between 2 and 32 chars")
    private String firstName;
    @NotBlank(message = "Last name cannot be empty")
    @Length(min = 2, max = 32, message = "Last name has to be between 2 and 32 chars")
    private String lastName;
    @Email(message = "Email should be in valid format")
    @NotBlank(message = "Email cannot be empty")
    private String email;
    @NotBlank(message = "Password cannot be empty")
    @Length(min = 5, message = "Password must contain at least 5 characters" )
    private String password;
    @NotBlank(message = "Username cannot be empty")
    @Length(min = 3, message = "Username must contain at least 3 characters" )
    private String username;
    @NotBlank(message = "Phone cannot be empty")
    @Pattern(regexp ="^\\+?[0-9]{10,}$|^\\+?[0-9]{1,3}[-\\s()]*[0-9]{6,}$", message = "Phone number has to be valid")
    private String phone;
    @PESEL(message = "PESEL is not valid")
    @NotBlank(message = "PESEL cannot be empty")
    private String pesel;
}
