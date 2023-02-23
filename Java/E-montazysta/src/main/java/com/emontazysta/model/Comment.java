package com.emontazysta.model;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.*;

import java.util.Date;
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

    private Date createdAt;

    @ManyToOne
    private AppUser messageCreator;

    @ManyToOne
    private OrderStage orderStage;

    @OneToMany(mappedBy = "comment")
    private List<Attachment> attachments;

}
