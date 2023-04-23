package com.emontazysta.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class DemandAdHoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime readByWarehousemanTime; //DELETE?
    private LocalDateTime realisationTime; //DELETE?
    private String warehousemanComment; // TODO: should be in other model if we want to have info about warehouseman + timestamp
    private String specialistComment; // TODO: should be in other model if we want to have info about specialist + timestamp
    // TODO: status values not defined

    @OneToMany(mappedBy = "demandAdHoc")
    private List<ToolRelease> toolReleases;

    @OneToMany(mappedBy = "demandAdHoc")
    private List<ElementReturnRelease> elementReturnReleases;

    @ManyToOne
    private WarehouseManager warehouseManager; //acceptedBy

    @ManyToOne
    private Warehouseman warehouseman; //DELETE?

    @ManyToOne
    private Specialist specialist; //acceptedBy

    @ManyToOne
    private Manager manager; //DELETE?

    @ManyToOne
    private Foreman createdBy;

    @ManyToOne
    private OrderStage orderStage;

    @OneToMany(mappedBy = "demandAdHoc")
    private List<ToolsPlannedNumber> listOfToolsPlannedNumber;

    @OneToMany(mappedBy = "demandAdHoc")
    private List<ElementsPlannedNumber> listOfElementsPlannedNumber;

    //TODO: relationship to OrderStage needed (many to many) should be replaced with association table in diagram
}
