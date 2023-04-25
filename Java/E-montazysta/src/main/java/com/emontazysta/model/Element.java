package com.emontazysta.model;

import com.emontazysta.enums.TypeOfUnit;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE element SET deleted = true WHERE id=?")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Element {

    public Element(Long id, String name, String code, TypeOfUnit typeOfUnit, float quantityInUnit,
                   List<ElementReturnRelease> elementReturnReleases, List<ElementInWarehouse> elementInWarehouses,
                   List<ElementEvent> elementEvents, Attachment attachment, List<ElementsPlannedNumber> listOfElementsPlannedNumber) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.typeOfUnit = typeOfUnit;
        this.quantityInUnit = quantityInUnit;
        this.elementReturnReleases = elementReturnReleases;
        this.elementInWarehouses = elementInWarehouses;
        this.elementEvents = elementEvents;
        this.attachment = attachment;
        this.listOfElementsPlannedNumber = listOfElementsPlannedNumber;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String code;
    private TypeOfUnit typeOfUnit;
    private float quantityInUnit;
    private boolean deleted = Boolean.FALSE;

    @OneToMany(mappedBy = "element")
    private List<ElementReturnRelease> elementReturnReleases;

    @OneToMany(mappedBy = "element")
    private List<ElementInWarehouse> elementInWarehouses;

    @OneToMany(mappedBy = "element")
    private List<ElementEvent> elementEvents;

    @OneToOne
    private Attachment attachment;

    @OneToMany(mappedBy ="element" )
    private List<ElementsPlannedNumber> listOfElementsPlannedNumber;
}
