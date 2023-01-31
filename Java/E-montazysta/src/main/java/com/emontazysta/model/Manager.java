package com.emontazysta.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
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
