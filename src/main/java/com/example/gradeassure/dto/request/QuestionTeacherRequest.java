package com.example.gradeassure.dto.request;

import com.example.gradeassure.model.enums.AnswerFormat;
import lombok.Data;

@Data
public class QuestionTeacherRequest {
    private String name;
    private AnswerFormat answerFormat;
    private int points;
}
