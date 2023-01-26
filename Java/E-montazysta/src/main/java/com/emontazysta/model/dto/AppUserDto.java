package com.emontazysta.model.dto;

import com.emontazysta.enums.Role;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@SuperBuilder
public class AppUserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String username;
    private Set<Role> roles;
}
