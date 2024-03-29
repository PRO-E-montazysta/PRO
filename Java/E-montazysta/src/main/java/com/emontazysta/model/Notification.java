package com.emontazysta.model;

import com.emontazysta.enums.NotificationType;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE notification SET deleted = true WHERE id=?")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Notification {

    public Notification(Long id, String content, LocalDateTime createdAt, LocalDateTime readAt, AppUser createdBy,
                        AppUser notifiedEmployee, OrderStage orderStage) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.readAt = readAt;
        this.createdBy = createdBy;
        this.notifiedEmployee = notifiedEmployee;
        this.orderStage = orderStage;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private NotificationType notificationType;

    private String content;

    private String subContent;

    private LocalDateTime createdAt;

    private LocalDateTime readAt;

    private boolean deleted = Boolean.FALSE;

    @ManyToOne
    private AppUser createdBy;

    @ManyToOne
    private AppUser notifiedEmployee;

    @ManyToOne
    private OrderStage orderStage;

    @ManyToOne
    private Orders order;

    @ManyToOne
    private ToolEvent toolEvent;

    @ManyToOne
    private ElementEvent elementEvent;

}
