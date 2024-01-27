package com.example.gradeassure.dto.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Builder
public class RequestStudentForAllResponse {
    private Long id;
    private int days;
    private String test;
    private String email;
}