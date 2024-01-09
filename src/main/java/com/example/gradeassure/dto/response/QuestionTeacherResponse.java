package com.example.gradeassure.dto.response;

import com.example.gradeassure.model.enums.AnswerFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.agent.builder.AgentBuilder;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionTeacherResponse {
    private Long id;
    private String question;
    private AnswerFormat answerFormat;
    private int points;
    private int numberOption;
    private List<OptionsTeacherResponse> optionsTeacherResponses;

    public QuestionTeacherResponse(Long id, String question, AnswerFormat answerFormat, int points, int numberOption) {
        this.id = id;
        this.question = question;
        this.answerFormat = answerFormat;
        this.points = points;
        this.numberOption = numberOption;
    }
}