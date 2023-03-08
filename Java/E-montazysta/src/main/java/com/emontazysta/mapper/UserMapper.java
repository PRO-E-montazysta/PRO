package com.emontazysta.mapper;

import com.emontazysta.model.AppUser;
import com.emontazysta.model.dto.AppUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public AppUserDto toDto(AppUser user) {
        return AppUserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .username(user.getUsername())
                .roles(user.getRoles())
                .build();
    }

    public static AppUser toEntity(AppUserDto appUserDto) {
        return AppUser.builder()
                .id(appUserDto.getId())
                .firstName(appUserDto.getFirstName())
                .lastName(appUserDto.getLastName())
                .email(appUserDto.getEmail())
                .password(appUserDto.getPassword())
                .username(appUserDto.getUsername())
                .roles(appUserDto.getRoles())
                .build();
    }
}
