package com.emontazysta.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE warehouse SET deleted = true WHERE id=?")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Warehouse {

    public Warehouse(Long id, String name, String description, String openingHours, Company company, Location location,
                     List<ElementInWarehouse> elementInWarehouses, List<Tool> tools) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.openingHours = openingHours;
        this.company = company;
        this.location = location;
        this.elementInWarehouses = elementInWarehouses;
        this.tools = tools;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String openingHours;

    private boolean deleted = Boolean.FALSE;

    @ManyToOne
    private Company company;

    @OneToOne
    private Location location;

    @OneToMany(mappedBy = "warehouse")
    private List<ElementInWarehouse> elementInWarehouses;

    @OneToMany(mappedBy = "warehouse")
    private List<Tool> tools;
}
