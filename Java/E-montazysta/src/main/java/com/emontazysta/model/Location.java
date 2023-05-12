package com.emontazysta.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE location SET deleted = true WHERE id=?")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Location {

    public Location(Long id, Double xCoordinate, Double yCoordinate, String city, String street,
                    String propertyNumber, String apartmentNumber, String zipCode, Orders orders, Warehouse warehouse) {
        this.id = id;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.city = city;
        this.street = street;
        this.propertyNumber = propertyNumber;
        this.apartmentNumber = apartmentNumber;
        this.zipCode = zipCode;
        this.order = orders;
        this.warehouse = warehouse;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double xCoordinate;

    private Double yCoordinate;

    private String city;

    private String street;

    private String propertyNumber;

    private String apartmentNumber;

    private String zipCode;

    private boolean deleted = Boolean.FALSE;
    
    @OneToOne(mappedBy = "location")
    private Orders order;

    @OneToOne(mappedBy = "location")
    private Warehouse warehouse;
}
