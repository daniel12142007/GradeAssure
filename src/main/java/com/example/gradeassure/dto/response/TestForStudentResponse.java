package com.example.gradeassure.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestForStudentResponse {
    private Long id;
    private boolean access;
    private String groupName;
    private LocalDateTime dateCreated;
    private long passed;
}