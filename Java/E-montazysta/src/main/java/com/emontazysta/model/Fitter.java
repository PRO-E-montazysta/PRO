package com.emontazysta.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class Fitter extends Employee {

    @ManyToMany
    private List<OrderStage> workingOn;
}
