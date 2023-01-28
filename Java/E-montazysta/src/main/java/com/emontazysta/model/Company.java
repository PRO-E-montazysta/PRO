package com.emontazysta.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String companyName;

    private Date createdAt;

//    @JsonManagedReference
//    @OneToMany
//    private List<Client> clients;

//    @JsonManagedReference
//    @OneToMany(mappedBy = "company")
//    private List<Orders> orders;

//    @JsonManagedReference
//    @OneToMany(mappedBy = "company")
//    private List<Warehouse> warehouses;

//    @JsonManagedReference
//    @OneToMany(mappedBy = "company")
//    private List<Employment> employments;
}
