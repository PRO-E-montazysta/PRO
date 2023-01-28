package com.emontazysta.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Manager extends Employee {

    @OneToMany
    private List<Unavailability> createdUnavailabilities;

    @OneToMany
    private List<ToolEvent> acceptedEvents;

    @OneToMany
    private List<Orders> managedOrders;

    @OneToMany
    private List<DemandAdHoc> demandsAdHocList;
}
