package com.emontazysta.model.dto;

import com.emontazysta.Role;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
public class AppUserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String username;
    private List<Role> roles;
}
