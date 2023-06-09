package com.emontazysta.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
public class ForemanDto extends FitterDto {
    private List<Long> assignedOrders;
    private List<Long> demandsAdHocs;
}

