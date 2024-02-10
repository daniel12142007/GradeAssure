package com.example.gradeassure.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.FilterJoinTable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestTeacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String subject;
    private LocalDateTime dateCreated;
    private int minScores;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @OneToOne
    @JoinColumn(name = "create_id")
    private RequestTeacher create;

    @ManyToMany
    @JoinTable(name = "testTeacher_requestTeacher",
            joinColumns = @JoinColumn(name = "test_teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "request_teacher_id"))
    private List<RequestTeacher> check;

    @OneToMany(mappedBy = "teacher")
    private List<RequestStudent> requestStudents;

    @OneToMany(mappedBy = "testTeacher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TestStudent> testStudents;

    @OneToOne
    @JoinColumn(name = "report_id")
    private Report report;

    @OneToMany(mappedBy = "testTeacher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QuestionTeacher> questionTeachers;
}
