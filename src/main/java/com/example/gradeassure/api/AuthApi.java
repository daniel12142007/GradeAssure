package com.example.gradeassure.api;

import com.example.gradeassure.dto.Response.UserResponse;
import com.example.gradeassure.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthApi {
     private final UserService userService;
    @GetMapping("sendcode")
    public UserResponse mars(@RequestParam String email){
        return  userService.send(email);

    }
    @GetMapping("checkpassword")
    public UserResponse marse(@RequestParam String password1, @RequestParam String password, @RequestParam String email){
        return userService.reset(password1, password, email);

    }
   @GetMapping("changepassword")
    public UserResponse marsel(@RequestParam String email, @RequestParam String code){

       return userService.isCodeValid(email,code);
   }

}
