package com.example.gradeassure.model;

import com.example.gradeassure.model.enums.Action;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestTeacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Action action;
    private String subject;
    private int days;
    private LocalDateTime dateCreated;
    private LocalDateTime dateAnswered;
    private LocalDateTime dateDeadline;
    private boolean answered;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @OneToOne(mappedBy = "create", cascade = CascadeType.ALL)
    private TestTeacher testTeacher;

    @ManyToMany(mappedBy = "check")
    private List<TestTeacher> testTeachers;
}
