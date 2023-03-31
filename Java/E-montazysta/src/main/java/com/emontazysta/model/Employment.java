package com.emontazysta.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Employment {

    public Employment(Long id, LocalDateTime dateOfEmployment, LocalDateTime dateOfDismiss, Company company, AppUser employee) {
        this.id = id;
        this.dateOfEmployment = dateOfEmployment;
        this.dateOfDismiss = dateOfDismiss;
        this.company = company;
        this.employee = employee;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateOfEmployment;

    private LocalDateTime dateOfDismiss;

    private boolean deleted = Boolean.FALSE;

    @ManyToOne
    private Company company;

    @ManyToOne
    private AppUser employee;

}
