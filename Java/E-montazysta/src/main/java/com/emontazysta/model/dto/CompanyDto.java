package com.emontazysta.model.dto;

import com.emontazysta.model.Warehouse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class CompanyDto {

    private Long id;
    private String companyName;
    private Date createdAt;
    private List<Warehouse> warehouse;
}
