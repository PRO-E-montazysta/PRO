package com.emontazysta.mapper;

import com.emontazysta.model.Fitter;
import com.emontazysta.model.dto.FitterDto;

public class FitterMapper {

    public static FitterDto toDto(Fitter fitter) {

        return FitterDto.builder()
                .id(fitter.getId())
                .firstName(fitter.getFirstName())
                .lastName(fitter.getLastName())
                .username(fitter.getUsername())
                .email(fitter.getEmail())
                .phone(fitter.getPhone())
                .pesel(fitter.getPesel())
                .build();
    }
}
