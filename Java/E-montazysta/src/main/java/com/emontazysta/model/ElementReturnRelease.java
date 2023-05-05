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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime releaseTime;
    private int releasedQuantity;
    private int returnedQuantity;
    private LocalDateTime returnTime;

    @ManyToOne
    private Warehouseman releasedBy;

    @ManyToOne
    private Element element;

    @ManyToOne
    private DemandAdHoc demandAdHoc;

    @ManyToOne
    private OrderStage orderStage;
}
