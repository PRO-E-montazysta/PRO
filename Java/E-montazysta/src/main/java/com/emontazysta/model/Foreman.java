package com.emontazysta.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
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
public class Foreman extends Fitter {

    @OneToMany(mappedBy = "managedBy")
    private List<OrderStage> ordersStagesList;

    @OneToMany(mappedBy = "receivedBy")
    private List<ToolRelease> receivedTools;

    @OneToMany(mappedBy = "receivedBy")
    private List<ElementReturnRelease> receivedElements;

    @OneToMany(mappedBy = "assignedTo")
    private List<Orders> assignedOrders;

    @OneToMany(mappedBy = "foreman")
    private List<ElementReturnRelease> elementReturnReleases;

    @OneToMany(mappedBy = "foreman")
    private List<DemandAdHoc> demandsAdHocs;
}
