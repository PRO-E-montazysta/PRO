package com.emontazysta.model;


import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE comment SET deleted = true WHERE id=?")
public class Comment {

    public Comment(Long id, String content, LocalDateTime createdAt, AppUser messageCreator, OrderStage orderStage,
                   List<Attachment> attachments) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.messageCreator = messageCreator;
        this.orderStage = orderStage;
        this.attachments = attachments;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private LocalDateTime createdAt;
    private boolean deleted = Boolean.FALSE;

    @ManyToOne
    private AppUser messageCreator;

    @ManyToOne
    private OrderStage orderStage;

    @OneToMany(mappedBy = "comment")
    private List<Attachment> attachments;

}
