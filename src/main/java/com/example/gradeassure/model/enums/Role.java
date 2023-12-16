package com.example.gradeassure.model.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMINSCHOOL,
    STUDENT,
    ADMIN,
    TEACHER;

    @Override
    public String getAuthority() {
        return name();
    }
}
