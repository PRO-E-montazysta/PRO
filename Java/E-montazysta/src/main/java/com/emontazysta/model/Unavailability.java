package com.emontazysta.model;

import com.emontazysta.enums.TypeOfUnavailability;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Unavailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private TypeOfUnavailability typeOfUnavailability;

    private String description;

    @NotNull
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date unavailableFrom;

    @NotNull
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date unavailableTo;

    @ManyToOne
    private AppUser employee;

    @ManyToOne
    private AppUser manager;
}
