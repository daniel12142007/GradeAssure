package com.example.gradeassure.model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String video;

    @OneToOne
    @JoinColumn(name = "questionStudent_id")
    private QuestionStudent answerVideo;
}
