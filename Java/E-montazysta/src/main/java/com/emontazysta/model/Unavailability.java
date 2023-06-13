package com.emontazysta.model;

import com.emontazysta.enums.TypeOfUnavailability;
import lombok.*;

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
public class Unavailability {

    public Unavailability(Long id, TypeOfUnavailability typeOfUnavailability, String description,
                          LocalDateTime unavailableFrom, LocalDateTime unavailableTo, AppUser assignedTo, Manager assignedBy, Long orderStageId) {
        this.id = id;
        this.typeOfUnavailability = typeOfUnavailability;
        this.description = description;
        this.unavailableFrom = unavailableFrom;
        this.unavailableTo = unavailableTo;
        this.assignedTo = assignedTo;
        this.assignedBy = assignedBy;
        this.orderStageId = orderStageId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private TypeOfUnavailability typeOfUnavailability;
    @Column(length=500)
    private String description;
    private LocalDateTime unavailableFrom;
    private LocalDateTime unavailableTo;
    private Long orderStageId;

    @ManyToOne
    private AppUser assignedTo;

    @ManyToOne
    private Manager assignedBy;
}
