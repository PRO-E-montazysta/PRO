package com.emontazysta.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ToolRelease {

    public ToolRelease(Long id, LocalDateTime releaseTime, LocalDateTime returnTime, Foreman receivedBy,
                       Warehouseman releasedBy, Tool tool, DemandAdHoc demandAdHoc, OrderStage orderStage) {
        this.id = id;
        this.releaseTime = releaseTime;
        this.returnTime = returnTime;
        this.receivedBy = receivedBy;
        this.releasedBy = releasedBy;
        this.tool = tool;
        this.demandAdHoc = demandAdHoc;
        this.orderStage = orderStage;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime releaseTime;
    private LocalDateTime returnTime;
    private boolean deleted = Boolean.FALSE;

    @ManyToOne
    private Foreman receivedBy;

    @ManyToOne
    private Warehouseman releasedBy;

    @ManyToOne
    private Tool tool;

    @ManyToOne
    private DemandAdHoc demandAdHoc;

    @ManyToOne
    private OrderStage orderStage;
}
