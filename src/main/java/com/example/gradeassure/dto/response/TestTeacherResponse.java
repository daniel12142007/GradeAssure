package com.example.gradeassure.dto.response;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
public class TestTeacherResponse {
    private Long id;
    private String name;
    private String subject;
    private LocalDateTime dateCreated;
    private int minScores;

}
