package com.example.gradeassure.model;

import com.example.gradeassure.model.enums.AnswerFormat;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class QuestionTeacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String question;
    private AnswerFormat answerFormat;
    private int points;

    @ManyToOne
    @JoinColumn(name = "testTeacher_id")
    private TestTeacher testTeacher;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OptionsTeacher> optionsTeachers;
}
