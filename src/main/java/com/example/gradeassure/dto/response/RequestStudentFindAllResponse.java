package com.example.gradeassure.dto.response;

import lombok.Data;

@Data
public class RequestStudentFindAllResponse {
    private Long id;
    private String testName;
    private int days;
    private String email;
}
