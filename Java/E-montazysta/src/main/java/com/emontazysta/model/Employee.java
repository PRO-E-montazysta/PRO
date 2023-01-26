package com.emontazysta.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String phone;

    @NotBlank
    private String pesel;

    @JsonManagedReference
    @OneToMany(mappedBy = "employee")
    private List<Employment> employments;

    @JsonManagedReference
    @OneToMany(mappedBy = "employee")
    private List<Unavailability> unavailabilities;

    @JsonManagedReference
    @OneToMany(mappedBy = "employee")
    private List<Notification> notifications;

    @JsonManagedReference
    @OneToMany(mappedBy = "messageCreator")
    private List<Comment> employeeComments;

    @JsonManagedReference
    @ManyToMany(mappedBy = "employees")
    private List<Comment>  allComments;

    @JsonManagedReference
    @OneToMany(mappedBy = "employee")
    private List<ElementEvent> events;

    @JsonManagedReference
    @OneToMany(mappedBy = "employee")
    private List<ToolEvent> toolEvents;

}
