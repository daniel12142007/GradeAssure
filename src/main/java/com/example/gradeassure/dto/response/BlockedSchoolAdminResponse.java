package com.example.gradeassure.dto.response;

import lombok.Data;

import javax.persistence.Id;

@Data
public class BlockedSchoolAdminResponse {
    private Long id;
    private String fullName;
    private String email;

}
