package com.emontazysta.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class OrderStage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private OrderStatus status;

    private BigDecimal price;

    private Integer order;

    private LocalDate plannedEndDate;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private long plannedDurationTime;

    private int plannedFittersNumber;

    private int minimumImagesNumber;

    @ManyToMany(mappedBy = "workingOn")
    private List<Fitter> assignedTo;

    @ManyToOne
    private Foreman managedBy;

    @OneToMany(mappedBy = "orderStage")
    private List<Comment> comments;

    @OneToMany(mappedBy = "orderStage")
    private List<ToolRelease> toolReleases;

    @OneToMany(mappedBy = "orderStage")
    private List<ElementReturnRelease> elementReturnReleases;

    @ManyToOne
    private Orders orders;

    @OneToMany(mappedBy = "orderStage")
    private List<Attachment> attachments;

    @OneToMany(mappedBy = "orderStage")
    private List<Notification> notifications;

    @ManyToMany
    private List<ToolType> tools;

    @ManyToMany
    private List<Element> elements;

    @ManyToMany
    private List<DemandAdHoc> demandsAdHoc;



    //TODO: relationship to Element, ToolType, Fitter, DemandAdHoc  needed (many to many) should be replaced with association table in diagram
}

