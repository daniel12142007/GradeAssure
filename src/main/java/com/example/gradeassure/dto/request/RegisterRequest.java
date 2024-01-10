package com.example.gradeassure.dto.request;

import com.example.gradeassure.dto.request.enums.RoleRequest;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class RegisterRequest {
    @NotNull(message = "Your fullName it is null!")
    @NotEmpty(message = "Your fullName it is empty!")
    private String fullName;
    @NotNull(message = "Your email it is null!")
    @NotEmpty(message = "Your email it is empty!")
    private String email;
    @NotNull(message = "Your password it is null!")
    @NotBlank(message = "Your password it is blank!")
    private String password;
    private RoleRequest roleRequest;

    public void setPassword(String password) {
        if (password.length() <= 6) {
            throw new RuntimeException("Пароль должен быть хотя бы 6 символов");
        } else if (!password.matches(".*[A-Z].*")) {
            throw new RuntimeException("Пароль должен содержать хотя бы 1 заглавную букву");
        } else if (!password.matches(".*\\d.*")) {
            throw new RuntimeException("Пароль должен содержать хотя бы 1 цифру");
        } else {
            this.password = password;
        }
    }

    public void setEmail(String email) {
        if (email != null && email.endsWith("@gmail.com")) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Некорректный email. Должен оканчиваться на @gmail.com");
        }
    }
}