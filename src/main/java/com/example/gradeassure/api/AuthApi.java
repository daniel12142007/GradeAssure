package com.example.gradeassure.api;

import com.example.gradeassure.dto.request.RegisterRequest;
import com.example.gradeassure.dto.response.JWTResponse;
import com.example.gradeassure.dto.response.UserResponse;
import com.example.gradeassure.service.UserService;
import com.example.gradeassure.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthApi {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/register")
    @PermitAll
    public JWTResponse register(
            @RequestBody @Valid RegisterRequest request) {
        return authService.register(request);
    }
    @PostMapping("/login")
    @PermitAll
    public JWTResponse login(@RequestParam
                             String email,
                             @RequestParam
                             String password) {
        return authService.login(email, password);
    }
    @GetMapping("sendcode")
    public UserResponse mars(@RequestParam String email) {
        return userService.send(email);
    }

    @GetMapping("checkpassword")
    public UserResponse marse(@RequestParam String password1, @RequestParam String password, @RequestParam String email) {
        return userService.reset(password1, password, email);

    }

    @GetMapping("changepassword")
    public UserResponse marsel(@RequestParam String email, @RequestParam String code) {
        return userService.isCodeValid(email, code);
    }


}