package com.example.gradeassure.api;

import com.example.gradeassure.dto.response.RequestTeacherResponse;
import com.example.gradeassure.service.RequestTeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @PreAuthorize("isAuthenticated()")
    public RequestTeacherResponse sendRequest(@RequestParam String subject, @RequestParam int days) {
        return requestTeacherService.sendRequestCreate(SecurityContextHolder.getContext().getAuthentication().getName(), subject, days);
    }
}