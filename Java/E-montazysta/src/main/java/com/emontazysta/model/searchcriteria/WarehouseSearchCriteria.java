package com.emontazysta.model.searchcriteria;

import com.emontazysta.model.Company;
import com.emontazysta.model.ElementInWarehouse;
import com.emontazysta.model.Location;
import com.emontazysta.model.Tool;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

public class WarehouseSearchCriteria {

    private String name;
    private String description;

    @NotBlank
    private String openingHours;

    @ManyToOne
    private Company company;

    @ManyToOne
    private Location location;

    @OneToMany(mappedBy = "warehouse")
    private List<ElementInWarehouse> elementInWarehouses;

    @OneToMany(mappedBy = "warehouse")
    private List<Tool> tools;
}
