package com.emontazysta.model;

import com.emontazysta.enums.TypeOfAttachment;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String url;
    private String description;
    private TypeOfAttachment typeOfAttachment;
    private LocalDateTime createdAt;

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
