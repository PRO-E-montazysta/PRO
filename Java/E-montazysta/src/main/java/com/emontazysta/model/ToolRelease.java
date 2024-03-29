package com.emontazysta.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE tool_release SET deleted = true WHERE id=?")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ToolRelease {

    public ToolRelease(Long id, LocalDateTime releaseTime, LocalDateTime returnTime,
                       Warehouseman releasedBy, Tool tool, DemandAdHoc demandAdHoc, OrderStage orderStage) {
        this.id = id;
        this.releaseTime = releaseTime;
        this.returnTime = returnTime;
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
    private Warehouseman releasedBy;

    @ManyToOne
    private Tool tool;

    @ManyToOne
    private DemandAdHoc demandAdHoc;

    @ManyToOne
    private OrderStage orderStage;
}
