package com.emontazysta.mapper;

import com.emontazysta.model.AppUser;
import com.emontazysta.model.dto.AppUserDto;

public class UserMapper {

    public static AppUserDto toDto(AppUser user) {
        return AppUserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .username(user.getUsername())
                .roles(user.getRoles())
                .build();
    }
}
