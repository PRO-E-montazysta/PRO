package com.emontazysta.model.searchcriteria;

import lombok.Data;

@Data
public class AppUserSearchCriteria {

    private String firstName;
    private String lastName;
    private String availableFrom;
    private String availableTo;
    private String email;
}
