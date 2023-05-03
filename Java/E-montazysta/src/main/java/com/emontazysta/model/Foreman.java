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
                  List<Attachment> attachments, List<ToolEvent> toolEvents, List<OrderStage> workingOn,
                  List<OrderStage> ordersStagesList, List<Orders> assignedOrders, List<DemandAdHoc> demandsAdHocs) {

        super(id, firstName, lastName, email, password, username, resetPasswordToken, roles, phone, pesel,
                unavailabilities, notifications, employeeComments, elementEvents, employments, attachments, toolEvents, workingOn);
        this.ordersStagesList = ordersStagesList;
        this.assignedOrders = assignedOrders;
        this.demandsAdHocs = demandsAdHocs;
    }

    @OneToMany(mappedBy = "managedBy")
    private List<OrderStage> ordersStagesList;

    @OneToMany(mappedBy = "assignedTo")
    private List<Orders> assignedOrders;

    @OneToMany(mappedBy = "foreman")
    private List<DemandAdHoc> demandsAdHocs;
}
