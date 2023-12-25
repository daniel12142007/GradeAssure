package com.example.gradeassure.dto.response;

import com.example.gradeassure.model.enums.Action;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
public class RequestTeacherResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Action action;
    private String subject;
    private int days;
    private LocalDateTime dateCreated;
    private LocalDateTime dateAnswered;
    private LocalDateTime dateDeadline;
    private boolean answered;
}
