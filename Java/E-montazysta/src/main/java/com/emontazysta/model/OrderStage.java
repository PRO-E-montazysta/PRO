package com.emontazysta.model;

import com.emontazysta.enums.OrderStageStatus;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE order_stage SET deleted = true WHERE id=?")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class OrderStage {

    public OrderStage(Long id, String name, OrderStageStatus status, BigDecimal price, LocalDateTime plannedStartDate,
                      LocalDateTime plannedEndDate, LocalDateTime startDate, LocalDateTime endDate, long plannedDurationTime,
                      int plannedFittersNumber, int minimumImagesNumber, List<Fitter> assignedTo,
                      List<Comment> comments, List<ToolRelease> toolReleases, List<ElementReturnRelease> elementReturnReleases,
                      Orders orders, List<Attachment> attachments, List<Notification> notifications,
                      List<ToolsPlannedNumber> listOfToolsPlannedNumber, List<ElementsPlannedNumber> listOfElementsPlannedNumber,
                      List<DemandAdHoc> demandsAdHoc) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.price = price;
        this.plannedStartDate = plannedStartDate;
        this.plannedEndDate = plannedEndDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.plannedDurationTime = plannedDurationTime;
        this.plannedFittersNumber = plannedFittersNumber;
        this.minimumImagesNumber = minimumImagesNumber;
        this.assignedTo = assignedTo;
        this.comments = comments;
        this.toolReleases = toolReleases;
        this.elementReturnReleases = elementReturnReleases;
        this.orders = orders;
        this.attachments = attachments;
        this.notifications = notifications;
        this.listOfToolsPlannedNumber = listOfToolsPlannedNumber;
        this.listOfElementsPlannedNumber = listOfElementsPlannedNumber;
        this.demandsAdHoc = demandsAdHoc;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private OrderStageStatus status;

    private BigDecimal price;

    private LocalDateTime plannedStartDate;

    private LocalDateTime plannedEndDate;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private long plannedDurationTime;

    private int plannedFittersNumber;

    private int minimumImagesNumber;

    private boolean deleted = Boolean.FALSE;

    @ManyToMany(mappedBy = "workingOn")
    private List<Fitter> assignedTo;

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

    @OneToMany(mappedBy = "orderStage")
    private List<ToolsPlannedNumber> listOfToolsPlannedNumber;

    @OneToMany(mappedBy = "orderStage")
    private List<ElementsPlannedNumber> listOfElementsPlannedNumber;

    @OneToMany(mappedBy = "orderStage")
    private List<DemandAdHoc> demandsAdHoc;
}

