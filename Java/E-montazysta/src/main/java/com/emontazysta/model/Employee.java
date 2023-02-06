package com.emontazysta.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public abstract class Employee extends AppUser {

    @NotBlank
    @Column(unique = true)
    private String phone;

    @NotBlank
    @Column(unique = true)
    private String pesel;


    @OneToMany(mappedBy = "employee")
    private List<Employment> employmentHistory;


    @OneToMany(mappedBy = "assignedTo")
    private List<Unavailability> unavailabilities;

    @OneToMany(mappedBy = "createdBy")
    private List<Notification> notifications;

    @OneToMany(mappedBy = "messageCreator")
    private List<Comment> employeeComments;

    @OneToMany(mappedBy = "updatedBy")
    private List<ElementEvent> elementEvents;

    @OneToMany(mappedBy = "employee")
    private List<Employment> employments;

    @OneToMany(mappedBy = "employee")
    private List<Attachment> attachments;

    @OneToMany(mappedBy = "updatedBy")
    private List<ToolEvent> toolEvents;

}
