package com.emontazysta.mapper;

import com.emontazysta.model.Element;
import com.emontazysta.model.dto.ElementDto;

public class ElementMapper {


    public static ElementDto elementToDto (Element element) {
        return ElementDto.builder()
                .id(element.getId())
                .name(element.getName())
                .code(element.getCode())
                .typeOfUnit(element.getTypeOfUnit())
                .quantityInUnit(element.getQuantityInUnit())
                .build();
    }
}
