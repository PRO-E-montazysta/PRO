package com.emontazysta.model;

import com.emontazysta.enums.TypeOfUnit;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

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

    private String name;
    private String code;
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

    @OneToMany(mappedBy ="element" )
    private List<ElementsPlannedNumber> listOfElementsPlannedNumber;

    public int getInWarehouseCount(Warehouse inWarehouse) {
        Optional<ElementInWarehouse> elementInWarehouseOptional = elementInWarehouses.stream().filter(o -> o.getWarehouse().equals(inWarehouse)).findFirst();
        if(elementInWarehouseOptional.isPresent()) {
            return elementInWarehouseOptional.get().getInWarehouseCount();
        }else {
            throw new EntityNotFoundException();
        }
    }

    public void changeInWarehouseCountByQuantity(Warehouse inWarehouse, int quantity) {
        Optional<ElementInWarehouse> elementInWarehouseOptional = elementInWarehouses.stream().filter(o -> o.getWarehouse().equals(inWarehouse)).findFirst();
        if(elementInWarehouseOptional.isPresent()) {
            ElementInWarehouse elementInWarehouse = elementInWarehouseOptional.get();
            elementInWarehouse.setInWarehouseCount(elementInWarehouse.getInWarehouseCount() + quantity);
        }else {
            throw new EntityNotFoundException();
        }
    }
}
