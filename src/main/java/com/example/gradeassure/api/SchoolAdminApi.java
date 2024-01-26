package com.example.gradeassure.api;

import com.example.gradeassure.dto.response.RequestStudentForAllResponse;
import com.example.gradeassure.dto.response.RequestTeacherForAllResponse;
import com.example.gradeassure.dto.response.SchoolAdminResponse;
import com.example.gradeassure.dto.response.StudentResponse;
import com.example.gradeassure.service.SchoolAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public List<RequestStudentForAllResponse> allowRequestStudent(@RequestParam Long id) {
        return schoolAdminService.allowById(id);
    }

    @PostMapping("refuse/request/student")
    @PreAuthorize("hasAnyAuthority('ADMINSCHOOL')")
    public List<RequestStudentForAllResponse> refuseRequestStudent(@RequestParam List<Long> ids) {
        return schoolAdminService.refuseByIdAll(ids);
    }

    @PutMapping("block/student")
    @PreAuthorize("hasAnyAuthority('ADMINSCHOOL')")
    public List<RequestStudentForAllResponse> block(@RequestParam List<Long> studentId) {
        return schoolAdminService.blockStudent(studentId);
    }

}
