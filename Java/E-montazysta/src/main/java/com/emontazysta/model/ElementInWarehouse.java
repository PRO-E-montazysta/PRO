package com.emontazysta.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE element_in_warehouse SET deleted = true WHERE id=?")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ElementInWarehouse {

    public ElementInWarehouse(Long id, int inWarehouseCount, int inUnitCount, String rack, String shelf,
                              Element element, Warehouse warehouse) {
        this.id = id;
        this.inWarehouseCount = inWarehouseCount;
        this.inUnitCount = inUnitCount;
        this.rack = rack;
        this.shelf = shelf;
        this.element = element;
        this.warehouse = warehouse;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int inWarehouseCount;
    private int inUnitCount;
    private String rack;
    private String shelf;
    private boolean deleted = Boolean.FALSE;

    @ManyToOne
    private Element element;

    @ManyToOne
    private Warehouse warehouse;
}