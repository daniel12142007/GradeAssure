package com.example.gradeassure.dto.Response;

import lombok.Data;

@Data

public class SchoolAdminResponse {

    private String fullName;
    private String email;

    public SchoolAdminResponse(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }
}