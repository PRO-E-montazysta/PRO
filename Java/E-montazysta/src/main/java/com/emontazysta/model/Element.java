package com.emontazysta.model;

import com.emontazysta.enums.TypeOfUnit;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Element {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    private String code;

    @NotNull
    private TypeOfUnit typeOfUnit;

    private float quantityInUnit;

    @OneToMany(mappedBy = "element")
    private List<ElementReturnRelease> elementReturnReleases;

    @OneToMany(mappedBy = "element")
    private List<ElementInWarehouse> elementInWarehouses;

    @OneToMany(mappedBy = "element")
    private List<ElementEvent> elementEvents;

    @OneToOne
    private Attachment attachment;

    @ManyToMany(mappedBy = "elements")
    private List<OrderStage> ordersStages;

    //TODO: relationship to OrderStage needed (many to many) should be replaced with association table in diagram
}
