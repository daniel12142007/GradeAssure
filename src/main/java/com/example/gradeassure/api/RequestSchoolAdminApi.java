package com.example.gradeassure.api;

import com.example.gradeassure.dto.request.RequestSchoolAdminRequest;
import com.example.gradeassure.dto.request.RequestTeacherRequest;
import com.example.gradeassure.dto.response.BlockedSchoolAdminResponse;
import com.example.gradeassure.repository.RequestSchoolAdminRepository;
import com.example.gradeassure.service.*;
import com.example.gradeassure.dto.response.RequestTeacherForAllResponse;
import com.example.gradeassure.dto.response.RequestTeacherResponse;
import com.example.gradeassure.service.RequestSchoolAdminService;
import com.example.gradeassure.service.RequestTeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public void processRequestSchoolAdmin(@RequestParam int days) {
        requestSchoolAdminService.processRequestSchoolAdmin(days);
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
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('SCHOOLADMIN')")
    public ResponseEntity<String> deleteTeacher(@RequestBody List<Long> teacherIds) {
    teacherService.deleteTeachers(teacherIds);
        return ResponseEntity.ok(" Teachers deleted");
    }
    @GetMapping("/unblock")
    @PreAuthorize("hasAnyAuthority('SCHOOLADMIN')")
    public ResponseEntity<List<BlockedSchoolAdminResponse>> getBlockedTeachersByIds(@RequestParam List<Long> teacherIds) {
        List<BlockedSchoolAdminResponse> unblockedTeachers = teacherService.getBlockedTeachersByIds(teacherIds);
        return new ResponseEntity<>(unblockedTeachers, HttpStatus.OK);
    }
    @DeleteMapping("/deletes")
    @PreAuthorize("hasAnyAuthority('SCHOOLADMIN')")
    public ResponseEntity<String> deleteStudents(@RequestBody List<Long> studentsIds) {
        studentService.deleteStudents(studentsIds);
        return ResponseEntity.ok(" students deleted");
    }
    @GetMapping("/unblocks")
    @PreAuthorize("hasAnyAuthority('SCHOOLADMIN')")
    public ResponseEntity<List<BlockedSchoolAdminResponse>>getBlockedStudentsByIds (@RequestParam List<Long> studentsIds) {
        List<BlockedSchoolAdminResponse> unblockedStudents = studentService.getBlockedStudentsByIds(studentsIds);
        return new ResponseEntity<>(unblockedStudents, HttpStatus.OK);
    }


}