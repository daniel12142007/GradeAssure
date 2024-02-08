package com.example.gradeassure.dto.response;

import com.example.gradeassure.model.enums.AnswerFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionStudentResponse {
    private Long id;
    private String question;
    private String video;
    private String audio;
    private AnswerFormat answerFormat;
    private List<OptionsResponse> option;

    public QuestionStudentResponse(Long id, String question, AnswerFormat answerFormat) {
        this.id = id;
        this.question = question;
        this.answerFormat = answerFormat;
    }
}