package com.emontazysta.model;

import com.emontazysta.enums.TypeOfUnavailability;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE unavailability SET deleted = true WHERE id=?")
public class Unavailability {

    public Unavailability(Long id, TypeOfUnavailability typeOfUnavailability, String description,
                          LocalDateTime unavailableFrom, LocalDateTime unavailableTo, AppUser assignedTo, Manager assignedBy) {
        this.id = id;
        this.typeOfUnavailability = typeOfUnavailability;
        this.description = description;
        this.unavailableFrom = unavailableFrom;
        this.unavailableTo = unavailableTo;
        this.assignedTo = assignedTo;
        this.assignedBy = assignedBy;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private TypeOfUnavailability typeOfUnavailability;

    private String description;
    private LocalDateTime unavailableFrom;
    private LocalDateTime unavailableTo;
    private boolean deleted = Boolean.FALSE;

    @ManyToOne
    private AppUser assignedTo;

    @ManyToOne
    private Manager assignedBy;
}
