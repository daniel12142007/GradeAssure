package com.example.gradeassure.dto.request;

import lombok.Data;

@Data
public class OptionsTeacherRequest {
    private String option;
    private Boolean correct;
    private String letter;
}
