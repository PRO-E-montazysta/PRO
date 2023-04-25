package com.emontazysta.model.dto;

import com.emontazysta.enums.TypeOfUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ElementDto {

    private Long id;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    private String code;
    @NotNull(message = "Type of unit cannot be empty")
    private TypeOfUnit typeOfUnit;
    @PositiveOrZero(message = "Quantity in unit cannot be negative")
    private float quantityInUnit;
    private List<Long> elementReturnReleases;
    private List<Long> elementInWarehouses;
    private List<Long> elementEvents;
    private Long attachmentId;
    private List<Long> ListOfElementsPlannedNumber;
}
