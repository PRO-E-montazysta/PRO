package com.emontazysta.mapper;

import com.emontazysta.model.Manager;
import com.emontazysta.model.dto.ManagerDto;

public class ManagerMapper {

    public static ManagerDto toDto(Manager manager) {

        return ManagerDto.builder()
                .id(manager.getId())
                .firstName(manager.getFirstName())
                .lastName(manager.getLastName())
                .username(manager.getUsername())
                .email(manager.getEmail())
                .phone(manager.getPhone())
                .pesel(manager.getPesel())
                .build();
    }
}
