package com.example.gradeassure.api;

import com.example.gradeassure.dto.response.TestForStudentResponse;
import com.example.gradeassure.service.TestStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/testStudent")
public class TestStudentApi {
    private final TestStudentService testStudentService;

    @GetMapping("find/all/test/for/take")
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public List<TestForStudentResponse> findAllTestStudent() {
        return testStudentService.findAllTestForStudent(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}