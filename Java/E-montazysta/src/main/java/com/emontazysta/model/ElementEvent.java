package com.emontazysta.model;

import com.emontazysta.enums.EventStatus;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE element_event SET deleted = true WHERE id=?")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ElementEvent {

    public ElementEvent(Long id, LocalDateTime eventDate, LocalDateTime movingDate, LocalDateTime completionDate,
                        String description, TypeOfStatus status, int quantity, Manager acceptedBy, AppUser updatedBy,
                        Element element, List<Attachment> attachments) {
        this.id = id;
        this.eventDate = eventDate;
        this.movingDate = movingDate;
        this.completionDate = completionDate;
        this.description = description;
        this.status = status;
        this.quantity = quantity;
        this.acceptedBy = acceptedBy;
        this.updatedBy = updatedBy;
        this.element = element;
        this.attachments = attachments;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime eventDate;
    private LocalDateTime movingDate;
    private LocalDateTime completionDate;
    private String description;
    private EventStatus status;
    private int quantity;
    private boolean deleted = Boolean.FALSE;

    @ManyToOne
    private Manager acceptedBy;

    @ManyToOne
    private AppUser createdBy;

    @ManyToOne
    private Element element;

    @OneToMany(mappedBy = "elementEvent")
    private List<Attachment> attachments;
}
