package com.example.gradeassure.dto.Response;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String fullName;
    private String email;
    private String code;
}