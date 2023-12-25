package com.example.gradeassure.dto.request;

import lombok.Data;

@Data
public class RegisterUserRequest {
    private String fullName;
    private String email;
    private String password;

    public void setEmail(String email) {
        if (email != null && email.endsWith("@gmail.com")) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Некорректный email. Должен оканчиваться на @gmail.com");
        }
    }
}
