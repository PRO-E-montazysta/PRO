package com.emontazysta.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Foreman extends Fitter {

    @OneToMany(mappedBy = "foreman")
    @JsonManagedReference
    private List<OrderStage> ordersStagesList;

    @OneToMany(mappedBy = "foreman")
    @JsonManagedReference
    private List<ToolRelease> toolReleaseList;

    @OneToMany(mappedBy = "foreman")
    @JsonManagedReference
    private List<ElementReturnRelease> elementsReturnsReleases;

    @OneToMany(mappedBy = "foreman")
    @JsonManagedReference
    private List<Orders> orders;

    @OneToMany(mappedBy = "foreman")
    @JsonManagedReference
    private List<DemandAdHoc> demandsAdHoc;


}
