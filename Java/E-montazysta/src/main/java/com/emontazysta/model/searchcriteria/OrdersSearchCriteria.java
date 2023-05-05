package com.emontazysta.model.searchcriteria;

import lombok.Data;

import java.util.List;

@Data
public class OrdersSearchCriteria {

    private String name;
    private List<String> status;
    private List<String> typeOfPriority;
    private String plannedStartMin;
    private String plannedStartMax;
}