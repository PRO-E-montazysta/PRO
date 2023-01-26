package com.emontazysta.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime readAt;

    private String sender;

    private String recipient;

    @ManyToOne
    @JsonBackReference
    private AppUser employee;

    // ToDo topic Enum (decision?)



}
