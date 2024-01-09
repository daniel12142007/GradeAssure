package com.example.gradeassure.dto.response;

import com.example.gradeassure.model.enums.AnswerFormat;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class QuestionTeacherResponse {
    private Long id;
    private String question;
    private AnswerFormat answerFormat;
    private int points;
}