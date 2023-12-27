package com.example.gradeassure.api;

import com.example.gradeassure.dto.Response.SchoolAdminResponse;
import com.example.gradeassure.model.SchoolAdmin;
import com.example.gradeassure.repository.SchoolAdminRepository;
import com.example.gradeassure.service.RequestSchoolAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/requestAdmin")
public class RequestAdminApi {
    private final RequestSchoolAdminService requestSchoolAdminService;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/approve-requests")
    public String approveRequests(@RequestBody List<Long> requestIds) {
        return requestSchoolAdminService.approveRequests(requestIds);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/reject-requests")
    public String rejectRequests(@RequestBody List<Long> requestIds) {
        return requestSchoolAdminService.rejectRequests(requestIds);
    }

    @PostMapping("/block-requests")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<List<SchoolAdminResponse>> blockRequests(@RequestBody List<Long> schoolAdminIds) {
        List<SchoolAdminResponse> responses = requestSchoolAdminService.blockSchoolAdmins(schoolAdminIds);
        return ResponseEntity.ok(responses);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/find-by-id-school-admin/{schoolAdminId}")
    public ResponseEntity<SchoolAdminResponse> look(@PathVariable Long schoolAdminId) {
        SchoolAdminResponse schoolAdmin = requestSchoolAdminService.findSchoolAdminById(schoolAdminId);
        return ResponseEntity.ok(schoolAdmin);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/find-all-unblocked-school-admins")
    public List<SchoolAdminResponse> getAllUnblockedSchoolAdmins() {
        return requestSchoolAdminService.getAllUnblockedSchoolAdmins();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/school-admin-look/{schoolAdminEmail}")
    public ResponseEntity<SchoolAdminResponse> getSchoolAdminDetails(@PathVariable String schoolAdminEmail) {
        SchoolAdminResponse schoolAdminResponse = requestSchoolAdminService.look(schoolAdminEmail);
        return ResponseEntity.ok(schoolAdminResponse);
    }


    @PostMapping("/block-school-admins")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<List<SchoolAdminResponse>> blockSchoolAdmins(@RequestBody List<Long> schoolAdminIds) {
        List<SchoolAdminResponse> responses = requestSchoolAdminService.blockSchoolAdmins(schoolAdminIds);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<String> deleteSchoolAdmins(@RequestBody List<Long> schoolAdminIds) {
        requestSchoolAdminService.deleteSchoolAdmins(schoolAdminIds);
        return ResponseEntity.ok("school admins deleted");
    }
}
