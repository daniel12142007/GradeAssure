package com.example.gradeassure.api;

import com.example.gradeassure.dto.request.OptionsTeacherRequest;
import com.example.gradeassure.dto.request.QuestionTeacherRequest;
import com.example.gradeassure.dto.response.*;
import com.example.gradeassure.service.TestStudentService;
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
//@PreAuthorize("hasAnyAuthority('TEACHER')")
public class TestTeacherApi {
    private final TestTeacherService teacherService;
    private final TestStudentService testStudentService;

    @GetMapping("find/all/test/for/teacher")
    public List<TestForStudentResponse> findAllTest(@RequestParam String email) {
        return teacherService.findAllTestForTeacher(email);
    }

    @GetMapping("find/by/test/{testId}")
    public TestTeacherResponse findByIdTest(@PathVariable Long testId) {
        return teacherService.findById(testId);
    }

    @GetMapping("find/by/question/{questionId}")
    public QuestionTeacherResponse findByIdQuestion(@PathVariable Long questionId) {
        return teacherService.findByIdQuestion(questionId);
    }

    @GetMapping("find/all/question/{testId}")
    public List<QuestionTeacherResponse> findAllQuestion(@PathVariable Long testId) {
        return teacherService.findAllQuestion(testId);
    }

    @PostMapping("create/test")
    public TestTeacherResponse createTest(@RequestParam("testName") String testName) {
        return teacherService.createTestTeacher(SecurityContextHolder.getContext().getAuthentication().getName(), testName);
    }

    @PostMapping("create/question")
    public List<QuestionTeacherResponse> createQuestion(@RequestBody QuestionTeacherRequest request,
                                                        @RequestParam("testId") Long testId) {
        return teacherService.createQuestion(request, testId);
    }

    @PostMapping("create/option")
    public QuestionTeacherResponse createOption(@RequestBody OptionsTeacherRequest request,
                                                @RequestParam("questionId") Long questionId) {
        return teacherService.createOption(request, questionId);
    }

    @PostMapping("min/option")
    public TestTeacherResponse minScores(@RequestParam("testId") Long testId,
                                         @RequestParam("scores") int scores) {
        return teacherService.minScores(testId, scores);
    }

    @GetMapping("find/result")
    public List<ResultResponse> findResult(
            @RequestParam String testName,
            @RequestParam String email
    ) {
        return testStudentService.findAllResultTest(testName, email);
    }
}