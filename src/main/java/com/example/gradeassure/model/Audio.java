package com.example.gradeassure.model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Audio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String audio;

    @OneToOne
    @JoinColumn(name = "questionStudent_id")
    private QuestionStudent answerAudio;
}
