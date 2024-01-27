package com.example.gradeassure.api;

import com.example.gradeassure.dto.response.BlockedSchoolAdminResponse;
import com.example.gradeassure.dto.response.RequestSchoolAdminResponse;
import com.example.gradeassure.service.*;
import com.example.gradeassure.dto.response.RequestTeacherForAllResponse;
import com.example.gradeassure.service.RequestSchoolAdminService;
import com.example.gradeassure.service.RequestTeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/requestSchoolAdmin")
public class RequestSchoolAdminApi {
    private final RequestSchoolAdminService requestSchoolAdminService;
    private final RequestTeacherService requestTeacherService;
    private final TeacherService teacherService;
    private final StudentService studentService;


    @PostMapping("/process")
    public RequestSchoolAdminResponse processRequestSchoolAdmin(@RequestParam int days) {
        return requestSchoolAdminService.processRequestSchoolAdmin(days, SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @PostMapping("allow/request/teacher")
    @PreAuthorize("hasAnyAuthority('ADMINSCHOOL')")
    public List<RequestTeacherForAllResponse> allowRequestTeacher(@RequestParam Long id) {
        return requestTeacherService.allowById(id);
    }

    @PostMapping("refuse/request/teacher")
    @PreAuthorize("hasAnyAuthority('ADMINSCHOOL')")
    public List<RequestTeacherForAllResponse> refuseRequestTeacher(@RequestParam List<Long> ids) {
        return requestTeacherService.refuseByIdAll(ids);
    }

    @GetMapping("find/all/request/teacher")
    @PreAuthorize("hasAnyAuthority('ADMINSCHOOL')")
    public List<RequestTeacherForAllResponse> findAllRequestTeacher() {
        return requestTeacherService.findAllRequest();
    }

    @PutMapping("block/teacher")
    @PreAuthorize("hasAnyAuthority('ADMINSCHOOL')")
    public List<RequestTeacherForAllResponse> block(@RequestParam List<Long> teacherId) {
        return requestTeacherService.blockedTeacher(teacherId);
    }

    @DeleteMapping("/delete/teacher")
    @PreAuthorize("hasAnyAuthority('ADMINSCHOOL')")
    public ResponseEntity<String> deleteTeacher(@RequestBody List<Long> teacherIds) {
        teacherService.deleteTeachers(teacherIds);
        return ResponseEntity.ok(" Teachers deleted");
    }

    @GetMapping("/unblock/teacher")
    @PreAuthorize("hasAnyAuthority('ADMINSCHOOL')")
    public ResponseEntity<List<BlockedSchoolAdminResponse>> getBlockedTeachersByIds(@RequestParam List<Long> teacherIds) {
        List<BlockedSchoolAdminResponse> unblockedTeachers = teacherService.getUnBlockedTeachersByIds(teacherIds);
        return new ResponseEntity<>(unblockedTeachers, HttpStatus.OK);
    }

    @DeleteMapping("/delete/student")
    @PreAuthorize("hasAnyAuthority('ADMINSCHOOL')")
    public ResponseEntity<String> deleteStudents(@RequestBody List<Long> studentsIds) {
        studentService.deleteStudents(studentsIds);
        return ResponseEntity.ok(" students deleted");
    }

    @GetMapping("/unblock/student")
    @PreAuthorize("hasAnyAuthority('ADMINSCHOOL')")
    public ResponseEntity<List<BlockedSchoolAdminResponse>> getBlockedStudentsByIds(@RequestParam List<Long> studentsIds) {
        List<BlockedSchoolAdminResponse> unblockedStudents = studentService.getUnBlockedStudentsByIds(studentsIds);
        return new ResponseEntity<>(unblockedStudents, HttpStatus.OK);
    }
}