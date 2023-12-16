package com.example.gradeassure.model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class OptionsTeacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String option;
    private Boolean correct;
    private String letter;

    @ManyToOne
    @JoinColumn(name = "questionTeacher_id")
    private QuestionTeacher teacher;

}
