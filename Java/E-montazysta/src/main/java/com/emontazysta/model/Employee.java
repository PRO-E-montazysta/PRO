package com.emontazysta.model;

import com.emontazysta.enums.Role;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public abstract class Employee extends AppUser {

    public Employee(Long id, String firstName, String lastName, String email, String password, String username,
                    String resetPasswordToken, Set<Role> roles, String phone, String pesel,
                    List<Unavailability> unavailabilities, List<Notification> notifications,
                    List<Comment> employeeComments, List<ElementEvent> elementEvents,
                    List<Employment> employments, List<Attachment> attachments, List<ToolEvent> toolEvents) {

        super(id, firstName, lastName, email, password, username, resetPasswordToken, roles);
        this.phone = phone;
        this.pesel = pesel;
        this.unavailabilities = unavailabilities;
        this.notifications = notifications;
        this.employeeComments = employeeComments;
        this.elementEvents = elementEvents;
        this.employments = employments;
        this.attachments = attachments;
        this.toolEvents = toolEvents;
    }

    private String phone;

    private String pesel;

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
