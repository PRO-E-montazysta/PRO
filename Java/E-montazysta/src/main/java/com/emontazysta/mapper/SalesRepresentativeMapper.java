package com.emontazysta.mapper;

import com.emontazysta.model.SalesRepresentative;
import com.emontazysta.model.dto.SalesRepresentativeDto;

public class SalesRepresentativeMapper {

    public static SalesRepresentativeDto toDto(SalesRepresentative salesRepresentative) {

        return SalesRepresentativeDto.builder()
                .id(salesRepresentative.getId())
                .firstName(salesRepresentative.getFirstName())
                .lastName(salesRepresentative.getLastName())
                .username(salesRepresentative.getUsername())
                .email(salesRepresentative.getEmail())
                .phone(salesRepresentative.getPhone())
                .pesel(salesRepresentative.getPesel())
                .build();
    }
}
