package com.example.gradeassure.api;

import com.example.gradeassure.dto.request.OptionsTeacherRequest;
import com.example.gradeassure.dto.request.QuestionTeacherRequest;
import com.example.gradeassure.dto.response.QuestionTeacherResponse;
import com.example.gradeassure.dto.response.TestTeacherResponse;
import com.example.gradeassure.service.TestTeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/test/teacher")
public class TestTeacherApi {
    private final TestTeacherService teacherService;

    @GetMapping("find/by/test/{testId}")
    @PreAuthorize("hasAnyAuthority('TEACHER')")
    public TestTeacherResponse findByIdTest(@PathVariable Long testId) {
        return teacherService.findById(testId);
    }

    @GetMapping("find/by/question/{questionId}")
    @PreAuthorize("hasAnyAuthority('TEACHER')")
    public QuestionTeacherResponse findByIdQuestion(@PathVariable Long questionId) {
        return teacherService.findByIdQuestion(questionId);
    }

    @GetMapping("find/all/question/{testId}")
    @PreAuthorize("hasAnyAuthority('TEACHER')")
    public List<QuestionTeacherResponse> findAllQuestion(@PathVariable Long testId) {
        return teacherService.findAllQuestion(testId);
    }

    @PostMapping("create/test")
    @PreAuthorize("hasAnyAuthority('TEACHER')")
    public TestTeacherResponse createTest(@RequestParam("testName") String testName) {
        return teacherService.createTestTeacher(SecurityContextHolder.getContext().getAuthentication().getName(), testName);
    }

    @PostMapping("create/question")
    @PreAuthorize("hasAnyAuthority('TEACHER')")
    public List<QuestionTeacherResponse> createQuestion(@RequestBody QuestionTeacherRequest request,
                                                        @RequestParam("testId") Long testId) {
        return teacherService.createQuestion(request, testId);
    }

    @PostMapping("create/option")
    @PreAuthorize("hasAnyAuthority('TEACHER')")
    public QuestionTeacherResponse createOption(@RequestBody OptionsTeacherRequest request,
                                                @RequestParam("questionId") Long questionId) {
        return teacherService.createOption(request, questionId);
    }

    @PostMapping("min/option")
    @PreAuthorize("hasAnyAuthority('TEACHER')")
    public TestTeacherResponse minScores(@RequestParam("testId") Long testId,
                                         @RequestParam("scores") int scores) {
        return teacherService.minScores(testId, scores);
    }
}