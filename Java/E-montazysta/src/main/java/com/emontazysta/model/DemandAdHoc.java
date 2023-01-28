package com.emontazysta.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class DemandAdHoc {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String description;
    private String comments;
    private LocalDateTime creationTime;
    private LocalDateTime readByWarehousemanTime;
    private LocalDateTime realisationTime;
    private String warehousemanComment; // TODO: should be in other model if we want to have info about warehouseman + timestamp
    private String specialistComment; // TODO: should be in other model if we want to have info about specialist + timestamp
    // TODO: status values not defined


    @ManyToOne
    private Manager manager;


    @ManyToOne
    private Foreman createdBy;
}
