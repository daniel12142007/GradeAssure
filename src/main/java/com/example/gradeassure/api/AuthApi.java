package com.example.gradeassure.api;

import com.example.gradeassure.dto.response.UserResponse;
import com.example.gradeassure.service.UserService;
import com.example.gradeassure.service.auth.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthApi {

    private final UserService userService;
    private final Auth auth;


    @PostMapping("save")
    public String save(@RequestParam String email, @RequestParam String password, @RequestParam String fullName) {
        auth.save(email, password, fullName);
        return "ok";
    }

    @PostMapping("login")
    public String save(@RequestParam String email, @RequestParam String password) {
        return auth.login(email, password);
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