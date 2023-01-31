package com.emontazysta.model;

import com.emontazysta.enums.TypeOfPriority;
import com.emontazysta.enums.TypeOfStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private TypeOfStatus typeOfStatus;

    @JsonFormat(pattern="dd-MM-yyyy")
    private Date plannedStart;

    @JsonFormat(pattern="dd-MM-yyyy")
    private Date plannedEnd;

    private Date createdAt;

    private Date editedAt;

    private TypeOfPriority typeOfPriority;

//    @ManyToOne
//    @JsonBackReference
//    private Company company;
//
//    @ManyToOne
//    @JsonBackReference
//    private AppUser managedBy;

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
