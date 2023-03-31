package com.emontazysta.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ElementReturnRelease {

    public ElementReturnRelease(Long id, LocalDateTime releaseTime, int releasedQuantity, int returnedQuantity,
                                LocalDateTime returnTime, Warehouseman servedBy, Element element, DemandAdHoc demandAdHoc,
                                Foreman foreman, OrderStage orderStage) {
        this.id = id;
        this.releaseTime = releaseTime;
        this.releasedQuantity = releasedQuantity;
        this.returnedQuantity = returnedQuantity;
        this.returnTime = returnTime;
        this.servedBy = servedBy;
        this.element = element;
        this.demandAdHoc = demandAdHoc;
        this.foreman = foreman;
        this.orderStage = orderStage;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime releaseTime;
    private int releasedQuantity;
    private int returnedQuantity;
    private LocalDateTime returnTime;
    private boolean deleted = Boolean.FALSE;

    @ManyToOne
    private Warehouseman servedBy;

    @ManyToOne
    private Element element;

    @ManyToOne
    private DemandAdHoc demandAdHoc;

    @ManyToOne
    private Foreman foreman;

    @ManyToOne
    private OrderStage orderStage;
}
