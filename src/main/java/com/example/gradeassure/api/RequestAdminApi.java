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

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/block-school-admins")
    public String blockRequests(@RequestBody List<Long> requestSchoolAdminId) {
        return requestSchoolAdminService.blockRequests(requestSchoolAdminId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/unblocked-school-admins")
    public List<SchoolAdmin> getAllUnblockedSchoolAdmins() {
        return requestSchoolAdminService.getAllUnblockedSchoolAdmins();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/block-requests")
    public String blockSchoolAdmins(@RequestBody List<Long> requestIds) {
        return requestSchoolAdminService.blockSchoolAdmins(requestIds);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/school-admin/{schoolAdminId}")
    public ResponseEntity<SchoolAdmin> getSchoolAdminById(@PathVariable Long schoolAdminId) {
        SchoolAdmin schoolAdmin = requestSchoolAdminService.findSchoolAdminById(schoolAdminId);
        return ResponseEntity.ok(schoolAdmin);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/school-admin/{schoolAdminId}")
    public ResponseEntity<SchoolAdminResponse> getSchoolAdminDetails(@PathVariable Long schoolAdminId) {
        SchoolAdminResponse schoolAdminResponse = requestSchoolAdminService.getSchoolAdminDetails(schoolAdminId);
        return ResponseEntity.ok(schoolAdminResponse);
    }
}
