package com.emontazysta.model;


import com.emontazysta.enums.Role;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Foreman extends Fitter {

    public Foreman(Long id, String firstName, String lastName, String email, String password, String username,
                  String resetPasswordToken, Set<Role> roles, String phone, String pesel,
                  List<Unavailability> unavailabilities, List<Notification> notifications,
                  List<Comment> employeeComments, List<ElementEvent> elementEvents, List<Employment> employments,
                  List<Attachment> attachments, List<ToolEvent> toolEvents, List<OrderStage> workingOn, List<ToolRelease> receivedTools, List<Orders> assignedOrders,
                  List<ElementReturnRelease> elementReturnReleases, List<DemandAdHoc> demandsAdHocs) {

        super(id, firstName, lastName, email, password, username, resetPasswordToken, roles, phone, pesel,
                unavailabilities, notifications, employeeComments, elementEvents, employments, attachments, toolEvents, workingOn);
        this.receivedTools = receivedTools;
        this.assignedOrders = assignedOrders;
        this.elementReturnReleases = elementReturnReleases;
        this.demandsAdHocs = demandsAdHocs;
    }

    @OneToMany(mappedBy = "receivedBy")
    private List<ToolRelease> receivedTools;

    @OneToMany(mappedBy = "assignedTo")
    private List<Orders> assignedOrders;

    @OneToMany(mappedBy = "foreman")
    private List<ElementReturnRelease> elementReturnReleases;

    @OneToMany(mappedBy = "createdBy")
    private List<DemandAdHoc> demandsAdHocs;
}
