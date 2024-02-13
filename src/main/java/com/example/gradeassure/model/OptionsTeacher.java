package com.example.gradeassure.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
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