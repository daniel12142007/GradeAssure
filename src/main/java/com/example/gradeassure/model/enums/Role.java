package com.example.gradeassure.model.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMINSCHOOL,
    ADMIN,
    TEACHER,
    STUDENT,
    USER,
    BLOCKED;

    @Override
    public String getAuthority() {
        return name();
    }
}
