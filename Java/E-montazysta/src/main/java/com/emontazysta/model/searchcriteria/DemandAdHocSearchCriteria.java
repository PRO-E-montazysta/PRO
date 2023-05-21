package com.emontazysta.model.searchcriteria;

import lombok.Data;

@Data
public class DemandAdHocSearchCriteria {

    private String dateFrom;
    private String dateTo;
    private String createdByName;
    private String orderStageName;
}
