package com.emontazysta.model.dto;

import com.emontazysta.model.Company;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ClientDto {

    private Long id;
    private String name;
    private String contactDetails;
    private Company company;
}
