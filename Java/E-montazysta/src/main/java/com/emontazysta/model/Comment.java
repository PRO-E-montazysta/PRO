package com.emontazysta.model;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String content;

    private LocalDateTime createdAt;

    @ManyToOne
    private AppUser messageCreator;

    @ManyToOne
    private OrderStage orderStage;

    @OneToMany(mappedBy = "comment")
    private List<Attachment> attachments;

}
