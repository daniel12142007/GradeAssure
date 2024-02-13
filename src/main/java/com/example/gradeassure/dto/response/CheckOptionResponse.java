package com.example.gradeassure.dto.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckOptionResponse {
    private Long id;
    private String option;
    private String letter;
    private boolean correct;
    private boolean chose;
}