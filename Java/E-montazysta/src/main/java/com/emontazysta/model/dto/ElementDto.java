package com.emontazysta.model.dto;

import com.emontazysta.enums.TypeOfUnit;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class ElementDto {

    private Long id;
    private String name;
    private String code;
    private TypeOfUnit typeOfUnit;
    private float quantityInUnit;
}
