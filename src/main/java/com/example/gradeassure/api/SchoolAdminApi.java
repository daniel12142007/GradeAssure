package com.example.gradeassure.api;

import com.example.gradeassure.dto.response.*;
import com.example.gradeassure.service.SchoolAdminService;
import com.example.gradeassure.service.TestStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/schoolAdmin")
public class SchoolAdminApi {
    private final SchoolAdminService schoolAdminService;
    private final TestStudentService testStudentService;

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

    @GetMapping("find/all/test/students")
    @PreAuthorize("hasAnyAuthority('ADMINSCHOOL')")
    public List<ResultResponse> findAllStudents(@RequestParam String testName) {
        return testStudentService.findAllResultTestSchoolAdmin(testName);
    }

    @GetMapping("find/all/students/users")
    @PreAuthorize("hasAnyAuthority('ADMINSCHOOL')")
    public List<UsersResponse> findAllStudents() {
        return schoolAdminService.findAllStudents();
    }

    @GetMapping("find/all/teachers/users")
    @PreAuthorize("hasAnyAuthority('ADMINSCHOOL')")
    public List<UsersResponse> findAllTeachers() {
        return schoolAdminService.findAllTeachers();
    }

    @GetMapping("find/by/teacher")
    @PreAuthorize("hasAnyAuthority('ADMINSCHOOL')")
    public TeacherOneResponse findByTeachers(@RequestParam Long teacherID) {
        return schoolAdminService.findByIdTeacher(teacherID);
    }

    @GetMapping("find/by/student")
    @PreAuthorize("hasAnyAuthority('ADMINSCHOOL')")
    public StudentOneResponse findByStudents(@RequestParam Long studentID) {
        return schoolAdminService.findByIdStudent(studentID);
    }
}