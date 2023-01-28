package com.emontazysta.model;

import com.emontazysta.enums.TypeOfUnavailability;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Data
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
    private AppUser assignedTo;

    @ManyToOne
    private AppUser assignedBy;
}
