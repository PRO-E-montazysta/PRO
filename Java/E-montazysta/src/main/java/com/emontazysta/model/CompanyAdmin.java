package com.emontazysta.model;

import com.emontazysta.enums.Role;
import com.emontazysta.model.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class CompanyAdmin extends Employee {

    public CompanyAdmin(Long id, String firstName, String lastName, String email, String password, String username,
                               String resetPasswordToken, Set<Role> roles, String phone, String pesel,
                               List<Unavailability> unavailabilities, List<Notification> notifications,
                               List<Comment> employeeComments, List<ElementEvent> elementEvents,
                               List<Employment> employments, List<Attachment> attachments, List<ToolEvent> toolEvents) {

        super(id, firstName, lastName, email, password, username, resetPasswordToken, roles, phone, pesel,
                unavailabilities, notifications, employeeComments, elementEvents, employments, attachments, toolEvents);
    }
}
