package com.emontazysta.model.dto;

import com.emontazysta.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AboutMeDto {

    private String firstName;
    private String lastName;
    private Set<Role> roles;
    private String companyName;
    private String profilePhotoUrl;
}
