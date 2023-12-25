package com.example.gradeassure.api;

import com.example.gradeassure.dto.request.RegisterUserRequest;
import com.example.gradeassure.dto.response.JWTResponse;
import com.example.gradeassure.dto.response.UserResponse;
import com.example.gradeassure.service.UserService;
import com.example.gradeassure.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthApi {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("login")
    public JWTResponse login(@RequestParam String email,@RequestParam String password){
        return authService.login(email,password);
    }

    @PostMapping("register")
    public JWTResponse register(@RequestBody RegisterUserRequest request) {
        return authService.register(request);
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