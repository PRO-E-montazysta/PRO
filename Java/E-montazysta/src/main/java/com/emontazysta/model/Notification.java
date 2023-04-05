package com.emontazysta.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE notification SET deleted = true WHERE id=?")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Notification {

    public Notification(Long id, String content, LocalDateTime createdAt, LocalDateTime readAt, AppUser createdBy,
                        List<AppUser> notifiedEmployees, OrderStage orderStage) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.readAt = readAt;
        this.createdBy = createdBy;
        this.notifiedEmployees = notifiedEmployees;
        this.orderStage = orderStage;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime readAt;

    private boolean deleted = Boolean.FALSE;

    @ManyToOne
    private AppUser createdBy;

    @OneToMany
    private List<AppUser> notifiedEmployees;

    @ManyToOne
    private OrderStage orderStage;

    // ToDo topic Enum



}
