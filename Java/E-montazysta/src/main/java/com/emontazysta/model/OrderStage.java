package com.emontazysta.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class OrderStage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private OrderStatus status;

    @NotNull
    private BigDecimal price;

    @NotBlank
    private String order;

    private LocalDate plannedEndDate;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @NotNull
    private long plannedDurationTime;

    @NotNull
    private int plannedFittersNumber;

    @NotNull
    private int minimumImagesNumber;

    @ManyToMany(mappedBy = "workingOn")
    private List<Fitter> assignedTo;

    @ManyToOne
    private AppUser managedBy;

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
}

