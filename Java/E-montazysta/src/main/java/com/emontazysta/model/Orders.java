package com.emontazysta.model;

import com.emontazysta.enums.OrderStatus;
import com.emontazysta.enums.TypeOfPriority;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE orders SET deleted = true WHERE id=?")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Orders {

    public Orders(Long id, String name, OrderStatus status, LocalDateTime plannedStart, LocalDateTime plannedEnd,
                  LocalDateTime createdAt, LocalDateTime editedAt, TypeOfPriority typeOfPriority, Company company,
                  Manager managedBy, Foreman assignedTo, Specialist specialist, SalesRepresentative salesRepresentative,
                  Location location, Client client, List<OrderStage> orderStages, List<Attachment> attachments) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.plannedStart = plannedStart;
        this.plannedEnd = plannedEnd;
        this.createdAt = createdAt;
        this.editedAt = editedAt;
        this.typeOfPriority = typeOfPriority;
        this.company = company;
        this.managedBy = managedBy;
        this.assignedTo = assignedTo;
        this.specialist = specialist;
        this.salesRepresentative = salesRepresentative;
        this.location = location;
        this.client = client;
        this.orderStages = orderStages;
        this.attachments = attachments;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private OrderStatus status;

    private LocalDateTime plannedStart;

    private LocalDateTime plannedEnd;

    private LocalDateTime createdAt;

    private LocalDateTime editedAt;

    private TypeOfPriority typeOfPriority;

    private boolean deleted = Boolean.FALSE;

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

    @OneToOne
    private Location location;

    @ManyToOne
    private Client client;

    @OneToMany(mappedBy = "orders")
    private List<OrderStage> orderStages;

    @OneToMany(mappedBy = "order")
    private List<Attachment> attachments;
}
