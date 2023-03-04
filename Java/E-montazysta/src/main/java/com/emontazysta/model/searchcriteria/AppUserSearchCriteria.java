package com.emontazysta.model.searchcriteria;

import lombok.Data;

@Data
public class AppUserSearchCriteria {

    private String id;
    private String firstname;
    private String lastName;
    private String availableFrom;
    private String availableTo;
    private String email;
}
