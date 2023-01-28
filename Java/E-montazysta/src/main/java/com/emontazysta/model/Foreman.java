package com.emontazysta.model;


import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Foreman extends Fitter {

    @OneToMany
    private List<OrderStage> ordersStagesList;

    @OneToMany
    private List<ToolRelease> receivedTools;

    @OneToMany
    private List<ElementReturnRelease> receivedElements;

    @OneToMany
    private List<Orders> assignedOrders;

    @OneToMany
    private List<DemandAdHoc> createdDemandsAdHoc;


}
