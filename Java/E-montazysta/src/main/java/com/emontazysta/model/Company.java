package com.emontazysta.model;

import com.emontazysta.enums.CompanyStatus;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String companyName;

    private Date createdAt;

    @NotNull
    private CompanyStatus status;

    private String statusReason;


    @OneToMany(mappedBy = "company")
    private List<Warehouse> warehouses;

    @OneToMany(mappedBy = "company")
    private List<Orders> orders;

    @OneToMany(mappedBy = "company")
    private List<Client> clients;

    @OneToMany(mappedBy = "company")
    private List<Employment> employments;
}
