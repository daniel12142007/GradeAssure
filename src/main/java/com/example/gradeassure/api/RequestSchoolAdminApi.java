package com.example.gradeassure.api;

import com.example.gradeassure.dto.request.RequestSchoolAdminRequest;
import com.example.gradeassure.dto.request.RequestTeacherRequest;
import com.example.gradeassure.dto.response.BlockedSchoolAdminResponse;
import com.example.gradeassure.repository.RequestSchoolAdminRepository;
import com.example.gradeassure.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/requestSchoolAdmin")
public class RequestSchoolAdminApi {
    private final RequestSchoolAdminService requestSchoolAdminService;
    private final TeacherService teacherService;
    private final StudentService studentService;


    @PostMapping("/process")
    public void processRequestSchoolAdmin(@RequestParam int days) {
        requestSchoolAdminService.processRequestSchoolAdmin(days);
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