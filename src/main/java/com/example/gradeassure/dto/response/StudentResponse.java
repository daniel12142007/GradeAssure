package com.example.gradeassure.dto.response;

import lombok.Data;

@Data
public class StudentResponse {
    private String fullName;
    private String email;

    public StudentResponse(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }

    public StudentResponse() {
    }
}
