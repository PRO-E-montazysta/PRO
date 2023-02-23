package com.emontazysta.model.searchcriteria;

import com.emontazysta.enums.TypeOfPriority;
import com.emontazysta.enums.TypeOfStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrdersSearchCriteria {

    private String name;
    private List<TypeOfStatus> typeOfStatus;
    private List<TypeOfPriority> typeOfPriority;
    private Date plannedStart;
}