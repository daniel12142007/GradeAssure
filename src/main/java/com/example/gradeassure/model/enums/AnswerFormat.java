package com.example.gradeassure.model.enums;

import org.springframework.security.core.GrantedAuthority;

public enum AnswerFormat implements GrantedAuthority {
    AUDIO,
    VIDEO,
    OPTION;

    @Override
    public String getAuthority() {
        return name();
    }
}
