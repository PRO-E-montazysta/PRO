package com.emontazysta.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE demand_ad_hoc SET deleted = true WHERE id=?")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class DemandAdHoc {

    public DemandAdHoc(Long id, String description, String comments, LocalDateTime creationTime,
                       LocalDateTime readByWarehousemanTime, LocalDateTime realisationTime, String warehousemanComment,
                       String specialistComment, List<ToolRelease> toolReleases, List<ElementReturnRelease> elementReturnReleases,
                       WarehouseManager warehouseManager, Warehouseman warehouseman, Specialist specialist, Manager manager,
                       Foreman foreman, List<OrderStage> ordersStages) {
        this.id = id;
        this.description = description;
        this.comments = comments;
        this.creationTime = creationTime;
        this.readByWarehousemanTime = readByWarehousemanTime;
        this.realisationTime = realisationTime;
        this.warehousemanComment = warehousemanComment;
        this.specialistComment = specialistComment;
        this.toolReleases = toolReleases;
        this.elementReturnReleases = elementReturnReleases;
        this.warehouseManager = warehouseManager;
        this.warehouseman = warehouseman;
        this.specialist = specialist;
        this.manager = manager;
        this.foreman = foreman;
        this.ordersStages = ordersStages;
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
