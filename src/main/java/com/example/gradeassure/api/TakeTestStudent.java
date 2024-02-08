package com.example.gradeassure.api;

import com.example.gradeassure.dto.response.TakeTestStudentResponse;
import com.example.gradeassure.service.TestStudentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("api/take/test/student")
@RequiredArgsConstructor
public class TakeTestStudent {
    private final TestStudentService testStudentService;

    @GetMapping("find/by/id/test")
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    @Operation(summary = "find the ID of the passing test", description = "in the figure the name of the page is Passing the test for the role Student")
    public TakeTestStudentResponse findByIdTakeTheTest(@RequestParam String email, @RequestParam String testName) {
        return testStudentService.takeTestStudent(email, testName);
    }

    @PostMapping("passing/option")
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public TakeTestStudentResponse passingOption(@RequestParam String email,
                                                 @RequestParam String testName,
                                                 @RequestParam Long optionId,
                                                 @RequestParam Long questionId) {
        return testStudentService.passingOption(email, testName, optionId, questionId);
    }

    @PostMapping("passing/option")
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public TakeTestStudentResponse passingVideo(@RequestParam String email,
                                                @RequestParam String testName,
                                                @RequestParam String video,
                                                @RequestParam Long questionId) {
        return testStudentService.passingVideo(email, testName, video, questionId);
    }

    @PostMapping("passing/option")
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public TakeTestStudentResponse passingAudio(@RequestParam String email,
                                                @RequestParam String testName,
                                                @RequestParam String audio,
                                                @RequestParam Long questionId) {
        return testStudentService.passingAudio(email, testName, audio, questionId);
    }
}