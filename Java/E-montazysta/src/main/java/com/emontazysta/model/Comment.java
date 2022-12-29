package com.emontazysta.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
