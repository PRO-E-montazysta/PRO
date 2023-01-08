package com.emontazysta.model;

import com.emontazysta.enums.TypeOfStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElementEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime eventDate;
    private LocalDateTime movingDate;
    private LocalDateTime completionDate;
    private String description;
    private TypeOfStatus status;
    private int quantity;
}
