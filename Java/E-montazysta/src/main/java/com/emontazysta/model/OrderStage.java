package com.emontazysta.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OrderStage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private OrderStatus status;
    private BigDecimal price;
    private String order;
    private LocalDate plannedEndDate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private long plannedDurationTime;
    private int plannedFittersNumber;
    private int minimumImagesNumber;
}

