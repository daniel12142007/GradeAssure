package com.example.gradeassure.model.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Action implements GrantedAuthority {
    CREATE,
    CHECK;

    @Override
    public String getAuthority() {
        return name();
    }
}
