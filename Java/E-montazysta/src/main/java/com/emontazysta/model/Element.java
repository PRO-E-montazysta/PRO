package com.emontazysta.model;

import com.emontazysta.enums.TypeOfUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Element {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    //TODO: Generate UNIQUE code
    private String code;

    private TypeOfUnit typeOfUnit;

    private float quantityInUnit;
}
