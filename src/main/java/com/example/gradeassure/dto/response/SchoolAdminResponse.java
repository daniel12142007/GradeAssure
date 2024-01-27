package com.example.gradeassure.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SchoolAdminResponse {
    private Long id;
    private String fullName;
    private String email;

    public SchoolAdminResponse() {
    }
}