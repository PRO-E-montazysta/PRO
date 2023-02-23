package com.emontazysta.model.dto;

import com.emontazysta.enums.TypeOfUnit;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
public class ElementDto {

    private Long id;
    private String name;
    private String code;
    private TypeOfUnit typeOfUnit;
    private float quantityInUnit;
    private List<Long> elementReturnReleases;
    private List<Long> elementInWarehouses;
    private List<Long> elementEvents;
    private Long attachmentId;
    private List<Long> ordersStages;
}
