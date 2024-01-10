package com.example.gradeassure.dto.request;

import com.example.gradeassure.model.enums.AnswerFormat;
import lombok.Data;

@Data
public class QuestionTeacherRequest {
    private String question;
    private AnswerFormat answerFormat;
    private int numberOption;
    private int points;
}
