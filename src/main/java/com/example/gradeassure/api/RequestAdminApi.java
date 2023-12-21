package com.example.gradeassure.api;

import com.example.gradeassure.service.RequestSchoolAdminService;
import lombok.RequiredArgsConstructor;
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
    public String blockSchoolAdmins(@RequestBody List<Long> schoolAdminIds) {
        return requestSchoolAdminService.blockSchoolAdmins(schoolAdminIds);
    }
}
