package com.example.gradeassure.dto.response;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
public class RequestSchoolAdminResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int days;
    private LocalDateTime dateCreated;
    private LocalDateTime dateAnswered;
    private LocalDateTime dateDeadline;
    private boolean answered;
}
