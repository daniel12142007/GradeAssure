package com.example.gradeassure.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {


    private String check(String password) {
        if (!isLengthValid(password)) {
            throw new RuntimeException(("Пароль должен быть хотя бы 6 символов"));
        } else if (!containsUpperCase(password)) {
            throw new RuntimeException(("Пароль должен содержать хотя бы 1 заглавную букву"));
        } else if (!containsDigit(password)) {
            throw new RuntimeException(("Пароль должен содержать хотя бы 1 цифру"));
        }
        return "ok";
    }

    private boolean isLengthValid(String password) {
        return password.length() >= 6;
    }

    private boolean containsUpperCase(String password) {
        return !password.equals(password.toLowerCase());
    }

    private boolean containsDigit(String password) {
        return password.chars().anyMatch(Character::isDigit);
    }
}