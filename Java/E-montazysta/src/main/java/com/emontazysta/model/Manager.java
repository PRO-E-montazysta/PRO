package com.emontazysta.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Manager extends Employee {

    @OneToMany(mappedBy = "assignedBy")
    private List<Unavailability> createdUnavailabilities;

    @OneToMany(mappedBy = "createdBy")
    private List<ToolEvent> acceptedEvents;

//    @JsonManagedReference
//    @OneToMany(mappedBy = "managedBy")
//    private List<Orders> managedOrders;

//    @JsonManagedReference
//    @OneToMany(mappedBy = "manager")
//    private List<DemandAdHoc> demandsAdHocList;
}
