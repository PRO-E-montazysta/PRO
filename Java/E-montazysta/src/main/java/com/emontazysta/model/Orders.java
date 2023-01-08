package com.emontazysta.model;

import com.emontazysta.enums.TypeOfPriority;
import com.emontazysta.enums.TypeOfStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private TypeOfStatus typeOfStatus;

    @JsonFormat(pattern="dd-MM-yyyy")
    private Date plannedStart;

    @JsonFormat(pattern="dd-MM-yyyy")
    private Date plannedEnd;

    private Date createdAt;

    private Date editedAt;

    private TypeOfPriority typeOfPriority;
}
