package com.emontazysta.model;

import com.emontazysta.enums.OrderStatus;
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
@SQLDelete(sql = "UPDATE demand_ad_hoc SET deleted = true WHERE id=?")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class DemandAdHoc {

    public DemandAdHoc(Long id, String description, LocalDateTime createdAt, List<ToolRelease> toolReleases,
                       List<ElementReturnRelease> elementReturnReleases, WarehouseManager warehouseManager,
                       Specialist specialist, Foreman createdBy, OrderStage orderStage,
                       List<ToolsPlannedNumber> listOfToolsPlannedNumber, List<ElementsPlannedNumber> listOfElementsPlannedNumber) {
        this.id = id;
        this.description = description;
        this.createdAt = createdAt;
        this.toolReleases = toolReleases;
        this.elementReturnReleases = elementReturnReleases;
        this.warehouseManager = warehouseManager;
        this.specialist = specialist;
        this.createdBy = createdBy;
        this.orderStage = orderStage;
        this.listOfToolsPlannedNumber = listOfToolsPlannedNumber;
        this.listOfElementsPlannedNumber = listOfElementsPlannedNumber;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private LocalDateTime createdAt;
    private boolean deleted = Boolean.FALSE;

    @OneToMany(mappedBy = "demandAdHoc")
    private List<ToolRelease> toolReleases;

    @OneToMany(mappedBy = "demandAdHoc")
    private List<ElementReturnRelease> elementReturnReleases;

    @ManyToOne
    private WarehouseManager warehouseManager; //acceptedBy

    @ManyToOne
    private Specialist specialist; //acceptedBy

    @ManyToOne
    private Foreman createdBy;

    @ManyToOne
    private OrderStage orderStage;

    @OneToMany(mappedBy = "demandAdHoc")
    private List<ToolsPlannedNumber> listOfToolsPlannedNumber;

    @OneToMany(mappedBy = "demandAdHoc")
    private List<ElementsPlannedNumber> listOfElementsPlannedNumber;

}
