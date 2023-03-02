package com.emontazysta.model.searchcriteria;

import lombok.Data;

import java.util.List;

@Data
public class ElementSearchCriteria {

    private String name;
    private String code;
    private List<String> typeOfUnit;
}