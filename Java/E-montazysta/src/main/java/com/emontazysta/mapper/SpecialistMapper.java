package com.emontazysta.mapper;

import com.emontazysta.model.Specialist;
import com.emontazysta.model.dto.SpecialistDto;

public class SpecialistMapper {

    public static SpecialistDto toDto(Specialist specialist) {

        return SpecialistDto.builder()
                .id(specialist.getId())
                .firstName(specialist.getFirstName())
                .lastName(specialist.getLastName())
                .username(specialist.getUsername())
                .email(specialist.getEmail())
                .phone(specialist.getPhone())
                .pesel(specialist.getPesel())
                .build();
    }
}
