package com.emontazysta.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE location SET deleted = true WHERE id=?")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Location {

    public Location(Long id, String name, Double xCoordinate, Double yCoordinate, String city, String street,
                    String propertyNumber, String apartmentNumber, String zipCode, List<Orders> orders, List<Warehouse> warehouses) {
        this.id = id;
        this.name = name;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.city = city;
        this.street = street;
        this.propertyNumber = propertyNumber;
        this.apartmentNumber = apartmentNumber;
        this.zipCode = zipCode;
        this.orders = orders;
        this.warehouses = warehouses;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double xCoordinate;

    private Double yCoordinate;

    private String city;

    private String street;

    private String propertyNumber;

    private String apartmentNumber;

    private String zipCode;

    private boolean deleted = Boolean.FALSE;

    @OneToMany(mappedBy = "location")
    private List<Orders> orders;

    @OneToMany(mappedBy = "location")
    private List<Warehouse> warehouses;
}
