package com.emontazysta.model.dto;

import com.emontazysta.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@SuperBuilder
@AllArgsConstructor
public class AppUserDto {

    private Long id;
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
    private Set<Role> roles;
}
