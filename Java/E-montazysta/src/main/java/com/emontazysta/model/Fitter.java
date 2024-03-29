package com.emontazysta.model;

import com.emontazysta.enums.Role;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.*;
import org.hibernate.annotations.SQLDelete;


import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE app_user SET deleted = true WHERE id=?")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Fitter extends Employee {

     public Fitter(Long id, String firstName, String lastName, String email, String password, String username,
                   String resetPasswordToken, Set<Role> roles, String phone, String pesel,
                   List<Unavailability> unavailabilities, List<Notification> notifications,
                   List<Comment> employeeComments, List<ElementEvent> elementEvents,
                   List<Employment> employments, List<Attachment> attachments, List<ToolEvent> toolEvents,
                   List<OrderStage> workingOn) {

          super(id, firstName, lastName, email, password, username, resetPasswordToken, roles, phone, pesel,
                  unavailabilities, notifications, employeeComments, elementEvents, employments, attachments, toolEvents);
          this.workingOn = workingOn;
     }

     @ManyToMany
     private List<OrderStage> workingOn;

     //TODO: relationship to OrderStage needed (many to many) should be replaced with association table in diagram
}
