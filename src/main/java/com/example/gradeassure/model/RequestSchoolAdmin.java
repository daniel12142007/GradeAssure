package com.example.gradeassure.model;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class RequestSchoolAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int days;
    private LocalDateTime dateCreated;
    private LocalDateTime dateAnswered;
    private LocalDateTime dateDeadline;
    private boolean answered;

    @Column(name = "overdue", nullable = false, columnDefinition = "boolean default false")
    private boolean overdue;

    @ManyToOne
    @JoinColumn(name = "schoolAdmin_id")
    private SchoolAdmin schoolAdmin;
}
