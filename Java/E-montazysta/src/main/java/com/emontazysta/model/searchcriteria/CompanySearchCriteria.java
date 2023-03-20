package com.emontazysta.model.searchcriteria;

import lombok.Data;

import java.util.List;

@Data
public class CompanySearchCriteria {

    private String companyName;
    private List<String> status;
}
