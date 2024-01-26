package com.example.gradeassure.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class RequestStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String testName;
    private int days;
    private LocalDateTime dateCreated;
    private LocalDateTime dateAnswered;
    private LocalDateTime dateDeadline;
    private boolean answered;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @OneToOne(mappedBy = "requestStudent", cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private TestTeacher teacher;
}