package com.emontazysta.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Manager extends Employee {

    @JsonManagedReference
    @OneToMany(mappedBy = "manager")
    private List<Unavailability> unavailabilities;

    @JsonManagedReference
    @OneToMany(mappedBy = "manager")
    private List<ToolEvent> events;

    @JsonManagedReference
    @OneToMany(mappedBy = "manager")
    private List<Orders> orders;

    @JsonManagedReference
    @OneToMany(mappedBy = "manager")
    private List<DemandAdHoc> demandsAdHoc;
}
