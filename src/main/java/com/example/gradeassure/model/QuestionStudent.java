package com.example.gradeassure.model;

import com.example.gradeassure.model.enums.AnswerFormat;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class QuestionStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String question;
    private AnswerFormat answerFormat;
    private int optionsMax;
    private int points;

    @ManyToOne
    @JoinColumn(name = "testStudent_id")
    private TestStudent testStudent;

    @OneToOne(mappedBy = "answerVideo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Video video;

    @OneToOne(mappedBy = "answerAudio", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Audio audio;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private OptionsStudent optionsStudent;
}