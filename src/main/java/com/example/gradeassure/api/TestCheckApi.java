package com.example.gradeassure.api;

import com.example.gradeassure.dto.response.CheckTestStudentResponse;
import com.example.gradeassure.dto.response.ResultResponse;
import com.example.gradeassure.service.TestStudentService;
import com.example.gradeassure.service.TestTeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/test/check")
@RequiredArgsConstructor
//@PreAuthorize("hasAnyAuthority('TEACHER')")
public class TestCheckApi {
    private final TestStudentService testStudentService;
    private final TestTeacherService testTeacherService;

    @GetMapping("find/by/id/student/test/check")
    public CheckTestStudentResponse check(@RequestParam Long testId,
                                          @RequestParam String email) {
        return testStudentService.findByIdTestStudentCheck(testId, email);
    }

    @PostMapping("checking/video")
    public CheckTestStudentResponse checkingVideo(
            @RequestParam Long testId,
            @RequestParam String email,
            @RequestParam int point,
            @RequestParam Long questionId
    ) {
        return testTeacherService.checkVideo(testId, email, point, questionId);
    }

    @PostMapping("checking/audio")
    public CheckTestStudentResponse checkingAudio(
            @RequestParam Long testId,
            @RequestParam String email,
            @RequestParam int point,
            @RequestParam Long questionId
    ) {
        return testTeacherService.checkAudio(testId, email, point, questionId);
    }

    @PostMapping("finish/check")
    public List<ResultResponse> finishCheck(
            @RequestParam Long testId,
            @RequestParam String email
    ) {
        return testTeacherService.finishCheck(testId,email);
    }
}
//@GetMapping("find/result")
//    public List<ResultResponse> findResult(
//            @RequestParam String testName,
//            @RequestParam String email
//    ) {
//        return testStudentService.findAllResultTest(testName, email);
//    }