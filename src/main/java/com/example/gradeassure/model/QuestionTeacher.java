package com.example.gradeassure.model;

import com.example.gradeassure.model.enums.AnswerFormat;

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
public class QuestionTeacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String question;
    private AnswerFormat answerFormat;
    private int points;
    private int numberOption;
    private LocalDateTime dateCreated;
    @ManyToOne
    @JoinColumn(name = "testTeacher_id")
    private TestTeacher testTeacher;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OptionsTeacher> optionsTeachers;
    @OneToMany(mappedBy = "questionTeacher")
    private List<QuestionStudent> questionStudents;
}
