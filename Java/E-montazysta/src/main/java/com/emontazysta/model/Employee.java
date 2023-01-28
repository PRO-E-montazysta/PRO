package com.emontazysta.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class Employee extends AppUser {

    @NotBlank
    @Column(unique = true)
    private String phone;

    @NotBlank
    @Column(unique = true)
    private String pesel;


    @OneToMany
    private List<Employment> employmentHistory;


    @OneToMany
    private List<Unavailability> unavailabilities;


    @OneToMany
    private List<Notification> notifications;


    @OneToMany
    private List<Comment> employeeComments;


    @ManyToMany
    private List<Comment>  allComments;

}
