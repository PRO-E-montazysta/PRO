package com.emontazysta.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Client {

    public Client(Long id, String name, String contactDetails, Company company, List<Orders> orders) {
        this.id = id;
        this.name = name;
        this.contactDetails = contactDetails;
        this.company = company;
        this.orders = orders;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String contactDetails;
    private boolean deleted = Boolean.FALSE;

    @ManyToOne
    private Company company;

    @OneToMany(mappedBy = "client")
    private List<Orders> orders;

}
