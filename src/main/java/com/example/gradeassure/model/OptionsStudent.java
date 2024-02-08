package com.example.gradeassure.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OptionsStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String option;
    private String letter;
    private Boolean correct;

    @OneToOne
    @JoinColumn(name = "questionStudent_id")
    private QuestionStudent student;
}
