package com.example.gradeassure.api;

import com.example.gradeassure.dto.response.*;
import com.example.gradeassure.service.SchoolAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/schoolAdmin")
public class SchoolAdminApi {
    private final SchoolAdminService schoolAdminService;

    @PostMapping("allow/request/student")
    @PreAuthorize("hasAnyAuthority('ADMINSCHOOL')")
    public List<RequestStudentFindAllResponse> allowRequestStudent(@RequestParam Long id) {
        return schoolAdminService.allowById(id);
    }

    @PostMapping("refuse/request/student")
    @PreAuthorize("hasAnyAuthority('ADMINSCHOOL')")
    public List<RequestStudentFindAllResponse> refuseRequestStudent(@RequestParam List<Long> ids) {
        return schoolAdminService.refuseByIdAll(ids);
    }

    @PutMapping("block/student")
    @PreAuthorize("hasAnyAuthority('ADMINSCHOOL')")
    public List<RequestStudentFindAllResponse> block(@RequestParam List<Long> studentId) {
        return schoolAdminService.blockStudent(studentId);
    }

}
