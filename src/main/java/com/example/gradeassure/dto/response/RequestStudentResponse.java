package com.example.gradeassure.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class RequestStudentResponse {
    private Long id;
    private String testName;
    private int days;
    private LocalDateTime dateCreated;
    private LocalDateTime dateAnswered;
    private LocalDateTime dateDeadline;
    private boolean answered;
}