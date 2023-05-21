package com.emontazysta.model;

import com.emontazysta.enums.CompanyStatus;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE company SET deleted = true WHERE id=?")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Company {

    public Company(Long id, String companyName, LocalDate createdAt, CompanyStatus status, String statusReason,
                   List<Warehouse> warehouses, List<Orders> orders, List<Client> clients, List<Employment> employments,
                   List<ToolType> toolTypes) {
        this.id = id;
        this.companyName = companyName;
        this.createdAt = createdAt;
        this.status = status;
        this.statusReason = statusReason;
        this.warehouses = warehouses;
        this.orders = orders;
        this.clients = clients;
        this.employments = employments;
        this.toolTypes = toolTypes;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;
    private LocalDate createdAt;
    private CompanyStatus status;
    private String statusReason;
    private boolean deleted = Boolean.FALSE;


    @OneToMany(mappedBy = "company")
    private List<Warehouse> warehouses;

    @OneToMany(mappedBy = "company")
    private List<Orders> orders;

    @OneToMany(mappedBy = "company")
    private List<Client> clients;

    @OneToMany(mappedBy = "company")
    private List<Employment> employments;

    @OneToMany(mappedBy = "company")
    private List<ToolType> toolTypes;
}
