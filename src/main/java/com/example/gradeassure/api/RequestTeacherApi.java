package com.example.gradeassure.api;

import com.example.gradeassure.dto.response.RequestTeacherFindByResponse;
import com.example.gradeassure.dto.response.RequestTeacherResponse;
import com.example.gradeassure.service.RequestTeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/request/Teacher")
public class RequestTeacherApi {
    private final RequestTeacherService requestTeacherService;

    @PostMapping("send/request/create")
//    @PreAuthorize("hasAnyAuthority('TEACHER')")
    public RequestTeacherResponse sendRequestCreate(@RequestParam String subject,
                                                    @RequestParam int days,
                                                    @RequestParam String email) {
        return requestTeacherService.sendRequestCreate(email, subject, days);
    }

    @PostMapping("send/request/check")
//    @PreAuthorize("hasAnyAuthority('TEACHER')")
    public RequestTeacherResponse sendCheck(@RequestParam List<Long> testId,
                                            @RequestParam int days,
                                            @RequestParam String email) {
        return requestTeacherService.sendRequestCheck(email, days, testId);
    }

    @GetMapping("find/by/id/request/check")
//    @PreAuthorize("hasAnyAuthority('TEACHER')")
    public RequestTeacherFindByResponse findByIdRequestTeacherFindByResponse(@RequestParam Long id) {
        return requestTeacherService.findByIdCheck(id);
    }
}