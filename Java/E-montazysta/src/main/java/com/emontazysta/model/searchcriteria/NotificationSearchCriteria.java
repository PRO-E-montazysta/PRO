package com.emontazysta.model.searchcriteria;

import lombok.Data;

import java.util.List;

@Data
public class NotificationSearchCriteria {

    private List<String> notificationTypes;
    private String createdAtMin;
    private String createdAtMax;

}
