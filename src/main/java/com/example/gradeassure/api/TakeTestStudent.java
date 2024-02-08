package com.example.gradeassure.api;

import com.example.gradeassure.dto.response.TakeTestStudentResponse;
import com.example.gradeassure.service.TestStudentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/take/test/student")
@RequiredArgsConstructor
public class TakeTestStudent {
    private final TestStudentService testStudentService;

    @GetMapping("find/by/id/test")
//    @PreAuthorize("hasAnyAuthority('STUDENT')")
    @Operation(summary = "find the ID of the passing test", description = "in the figure the name of the page is Passing the test for the role Student")
    public TakeTestStudentResponse findByIdTakeTheTest(@RequestParam String email, @RequestParam String testName) {
        return testStudentService.takeTestStudent(email, testName);
    }
}