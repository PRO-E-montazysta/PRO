package com.emontazysta.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@Builder
public class CompanyDto {

    private Long id;
    private String companyName;
    private Date createdAt;
}
