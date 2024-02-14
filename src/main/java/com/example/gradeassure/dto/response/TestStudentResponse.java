package com.example.gradeassure.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestStudentResponse {
    private Long id;
    private String testName;
    private LocalDateTime datePassing;
    private long passed;
}