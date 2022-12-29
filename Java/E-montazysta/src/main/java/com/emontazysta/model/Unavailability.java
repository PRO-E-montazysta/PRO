package com.emontazysta.model;

import com.emontazysta.enums.TypeOfUnavailability;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Unavailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private TypeOfUnavailability typeOfUnavailability;

    private String description;

    @JsonFormat(pattern="dd-MM-yyyy")
    private Date unavailableFrom;

    @JsonFormat(pattern="dd-MM-yyyy")
    private Date unavailableTo;
}
