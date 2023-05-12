package com.emontazysta.model;

import com.emontazysta.enums.EventStatus;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE tool_event SET deleted = true WHERE id=?")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ToolEvent {

    public ToolEvent(Long id, LocalDateTime eventDate, LocalDateTime movingDate, LocalDateTime completionDate,
                     String description, EventStatus status, AppUser createdBy, Manager acceptedBy, Tool tool,
                     List<Attachment> attachments) {
        this.id = id;
        this.eventDate = eventDate;
        this.movingDate = movingDate;
        this.completionDate = completionDate;
        this.description = description;
        this.status = status;
        this.createdBy = createdBy;
        this.acceptedBy = acceptedBy;
        this.tool = tool;
        this.attachments = attachments;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime eventDate;
    private LocalDateTime movingDate;
    private LocalDateTime completionDate;
    private String description;
    private boolean deleted = Boolean.FALSE;
    private EventStatus status;

    @ManyToOne
    private AppUser createdBy;

    @ManyToOne
    private Manager acceptedBy;

    @ManyToOne
    private Tool tool;

    @OneToMany(mappedBy = "toolEvent")
    private List<Attachment> attachments;
}
