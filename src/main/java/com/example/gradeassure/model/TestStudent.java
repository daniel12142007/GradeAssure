package com.example.gradeassure.model;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class TestStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int results;
    private LocalDateTime dateCreated;
    private boolean passed;
    private boolean checked;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "testTeacher_id")
    private TestTeacher testTeacher;

    @OneToMany(mappedBy = "testStudent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QuestionStudent> questionStudents;
}
