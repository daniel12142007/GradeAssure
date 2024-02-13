package com.example.gradeassure.dto.response;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckTestStudentResponse {
    private Long id;
    private List<CheckQuestionTeacherResponse> questions;

    public CheckTestStudentResponse(Long id) {
        this.id = id;
    }
}