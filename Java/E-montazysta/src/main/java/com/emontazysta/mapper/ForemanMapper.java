package com.emontazysta.mapper;

import com.emontazysta.model.Foreman;
import com.emontazysta.model.dto.ForemanDto;

public class ForemanMapper {

    public static ForemanDto toDto(Foreman foreman) {

        return ForemanDto.builder()
                .id(foreman.getId())
                .firstName(foreman.getFirstName())
                .lastName(foreman.getLastName())
                .username(foreman.getUsername())
                .email(foreman.getEmail())
                .phone(foreman.getPhone())
                .pesel(foreman.getPesel())
                .toolReleaseList(foreman.getReceivedTools())
                .build();
    }
}
