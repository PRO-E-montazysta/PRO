package com.emontazysta.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import java.util.Date;

@Entity
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;

    private Date createdAt;

    public Comment() {
        this.createdAt = new Date();
    }

    public Comment(String content) {
        this.content = content;
        this.createdAt = new Date();
    }
}
