package com.example.gradeassure.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TakeTestStudentResponse {
    private Long testId;
    private String name;
    private List<QuestionStudentResponse> questionStudentResponses;

    public TakeTestStudentResponse(Long testId, String name) {
        this.testId = testId;
        this.name = name;
    }
}