package com.emontazysta.model;

import com.emontazysta.enums.TypeOfStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ElementEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime eventDate;

    private LocalDateTime movingDate;

    private LocalDateTime completionDate;

    private String description;

    @NotNull
    private TypeOfStatus status;

    @NotNull
    private int quantity;

    @ManyToOne
    private AppUser createdBy;

    @ManyToOne
    private Element element;
}
