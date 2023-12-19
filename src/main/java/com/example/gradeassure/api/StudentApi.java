package com.example.gradeassure.api;

import com.example.gradeassure.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/student")
public class StudentApi {
    private final UserService userService;

}
