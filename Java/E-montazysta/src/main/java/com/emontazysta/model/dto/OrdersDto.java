package com.emontazysta.model.dto;

import com.emontazysta.enums.TypeOfPriority;
import com.emontazysta.enums.TypeOfStatus;
import lombok.Builder;
import lombok.Data;
import java.util.Date;

@Data
@Builder
public class OrdersDto {

    private Long id;
    private String name;
    private TypeOfStatus typeOfStatus;
    private Date plannedStart;
    private Date plannedEnd;
    private Date createdAt;
    private Date editedAt;
    private TypeOfPriority typeOfPriority;
}
