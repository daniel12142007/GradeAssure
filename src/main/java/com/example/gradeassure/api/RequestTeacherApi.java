package com.example.gradeassure.api;

import com.example.gradeassure.dto.response.RequestTeacherResponse;
import com.example.gradeassure.service.RequestTeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/requestTeacher")
public class RequestTeacherApi {
    private final RequestTeacherService requestTeacherService;

    @PostMapping("send/request")
    public RequestTeacherResponse sendRequest(@RequestParam String subject, @RequestParam int days, @RequestParam String email) {
        return requestTeacherService.sendRequestCreate(email, subject, days);
    }


}