package com.emontazysta.model.searchcriteria;

import com.emontazysta.enums.TypeOfPriority;
import com.emontazysta.enums.TypeOfStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrdersSearchCriteria {

    private String name;
    private List<String> typeOfStatus;
    private List<String> typeOfPriority;
    private String plannedStartMin;
    private String plannedStartMax;
}