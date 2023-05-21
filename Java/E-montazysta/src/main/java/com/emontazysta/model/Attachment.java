package com.emontazysta.model;

import com.emontazysta.enums.TypeOfAttachment;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE attachment SET deleted = true WHERE id=?")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Attachment {

    public Attachment(Long id, String name, String url, String description, TypeOfAttachment typeOfAttachment,
                      LocalDateTime createdAt, ToolType toolType, Comment comment, AppUser employee, ToolEvent toolEvent,
                      Orders order, Element element, OrderStage orderStage, ElementEvent elementEvent) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.description = description;
        this.typeOfAttachment = typeOfAttachment;
        this.createdAt = createdAt;
        this.toolType = toolType;
        this.comment = comment;
        this.employee = employee;
        this.toolEvent = toolEvent;
        this.order = order;
        this.element = element;
        this.orderStage = orderStage;
        this.elementEvent = elementEvent;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String url;
    private String description;
    private TypeOfAttachment typeOfAttachment;
    private LocalDateTime createdAt;
    private String fileName;
    private boolean deleted = Boolean.FALSE;

    @ManyToOne
    private ToolType toolType;

    @ManyToOne
    private Comment comment;

    @ManyToOne
    private AppUser employee;

    @ManyToOne
    private ToolEvent toolEvent;

    @ManyToOne
    private Orders order;

    @OneToOne(mappedBy = "attachment")
    private Element element;

    @ManyToOne
    private OrderStage orderStage;

    @ManyToOne
    private ElementEvent elementEvent;
}
