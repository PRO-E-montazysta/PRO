package com.emontazysta.model.searchcriteria;

import lombok.Data;

import java.util.List;

@Data
public class EventSearchCriteria {

    private String itemName;
    private List<String> typeOfStatus;
    private String eventDateMin;
    private String eventDateMax;
    private List<String> eventType;
}
