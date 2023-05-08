package com.emontazysta.model;

import com.emontazysta.enums.NotificationType;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private NotificationType notificationType;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime readAt;

    @ManyToOne
    private AppUser createdBy;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<AppUser> notifiedEmployees;

    @ManyToOne
    private OrderStage orderStage;

    @ManyToOne
    private Orders order;

}
