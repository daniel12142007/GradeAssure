package com.example.gradeassure.api;

import com.example.gradeassure.dto.response.TestForStudentResponse;
import com.example.gradeassure.dto.response.TestStudentResponse;
import com.example.gradeassure.model.RequestStudent;
import com.example.gradeassure.service.RequestStudentService;
import com.example.gradeassure.service.TestStudentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("get/day/deadline")
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public long dayDeadline() {
        return testStudentService.countTime(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @GetMapping("find/all/test")
    @Operation(summary = "page in figma Test")
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public List<TestStudentResponse> findAllTestStudent(@RequestParam String email) {
        return testStudentService.findAllTestStudent(email);
    }
}