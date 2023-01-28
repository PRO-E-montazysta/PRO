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
public class Foreman extends Fitter {

    @OneToMany(mappedBy = "managedBy")
    private List<OrderStage> ordersStagesList;

    @OneToMany(mappedBy = "recivedBy")
    private List<ToolRelease> receivedTools;

    @OneToMany(mappedBy = "receivedBy")
    private List<ElementReturnRelease> receivedElements;

    @OneToMany(mappedBy = "assignedTo")
    private List<Orders> assignedOrders;

    @OneToMany(mappedBy = "createdBy")
    private List<DemandAdHoc> createdDemandsAdHoc;


}
