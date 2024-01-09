package com.example.gradeassure.api;

import com.example.gradeassure.dto.response.RequestTeacherForAllResponse;
import com.example.gradeassure.dto.response.RequestTeacherResponse;
import com.example.gradeassure.service.RequestSchoolAdminService;
import com.example.gradeassure.service.RequestTeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/requestSchoolAdmin")
public class RequestSchoolAdminApi {
    private final RequestSchoolAdminService requestSchoolAdminService;
    private final RequestTeacherService requestTeacherService;

    @PostMapping("/process")
    public void processRequestSchoolAdmin(@RequestParam int days) {
        requestSchoolAdminService.processRequestSchoolAdmin(days);
    }

    @GetMapping("find/all/request/teacher")
    @PreAuthorize("hasAnyAuthority('ADMINSCHOOL')")
    public List<RequestTeacherForAllResponse> findAllRequestTeacher() {
        return requestTeacherService.findAllRequest();
    }

    @PutMapping("refuse/request/teacher")
    public List<RequestTeacherForAllResponse> refuseRequestTeacher(@RequestParam List<Long> ids) {
        return requestTeacherService.refuseByIdAll(ids);
    }
}