package com.emontazysta.model.searchcriteria;

import lombok.Data;
import java.util.List;

@Data
public class AppUserSearchCriteria {

    private String firstName;
    private String lastName;
    private String availableFrom;
    private String availableTo;
    private String email;
    private String pesel;
    private String phone;
    private List<String> roles;
}
