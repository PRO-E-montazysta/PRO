package com.emontazysta.model;

import com.emontazysta.enums.TypeOfPriority;
import com.emontazysta.enums.TypeOfStatus;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private TypeOfStatus typeOfStatus;

    @NotNull
    private LocalDateTime plannedStart;

    @NotNull
    private LocalDateTime plannedEnd;

    private LocalDateTime createdAt;

    private LocalDateTime editedAt;

    @NotNull
    private TypeOfPriority typeOfPriority;

    @ManyToOne
    private Company company;

    @ManyToOne
    private Manager managedBy;

    @ManyToOne
    private Foreman assignedTo;

    @ManyToOne
    private Specialist specialist;

    @ManyToOne
    private SalesRepresentative salesRepresentative;

    @ManyToOne
    private Location location;

    @ManyToOne
    private Client client;

    @OneToMany(mappedBy = "orders")
    private List<OrderStage> orderStages;

    @OneToMany(mappedBy = "order")
    private List<Attachment> attachments;
}
