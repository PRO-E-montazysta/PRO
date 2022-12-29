package com.emontazysta.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ElementReturnRelease {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime releaseTime;
    private int releasedQuantity;
    private int returnedQuantity;
    private LocalDateTime returnTime;
}
