package com.example.gradeassure.dto.response;

import com.example.gradeassure.model.enums.AnswerFormat;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckQuestionTeacherResponse {
    private Long id;
    private String question;
    private List<CheckOptionResponse> options;
    private String video;
    private String audio;
    private int maxPoint;
    private int point;
    private AnswerFormat answerFormat;

    public CheckQuestionTeacherResponse(Long id, String question, int point, int maxPoint, AnswerFormat answerFormat) {
        this.id = id;
        this.question = question;
        this.point = point;
        this.answerFormat = answerFormat;
        this.maxPoint = maxPoint;
    }
}