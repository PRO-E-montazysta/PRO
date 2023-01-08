package com.emontazysta.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElementInWarehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int inWarehouseCount;
    private int inUnitCount;
    private String rack;
    private String shelf;



}

