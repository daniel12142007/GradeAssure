package com.example.gradeassure.dto.response;

import com.example.gradeassure.model.enums.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JWTResponse {
    private String email;
    private String token;
    private String message;
    private Role role;
}
