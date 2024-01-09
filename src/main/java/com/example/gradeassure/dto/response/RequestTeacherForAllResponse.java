package com.example.gradeassure.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestTeacherForAllResponse {
    private Long id;
    private String email;
    private Long create;
    private Long check;
}