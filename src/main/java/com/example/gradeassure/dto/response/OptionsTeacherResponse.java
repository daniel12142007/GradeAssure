package com.example.gradeassure.dto.response;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class OptionsTeacherResponse {
    private Long id;
    private String option;
    private String letter;

    private Boolean correct;
}
