package com.example.gradeassure.api;

import com.example.gradeassure.dto.request.RequestSchoolAdminRequest;
import com.example.gradeassure.repository.RequestSchoolAdminRepository;
import com.example.gradeassure.service.RequestSchoolAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/requestSchoolAdmin")
public class RequestSchoolAdminApi {
    private final RequestSchoolAdminService requestSchoolAdminService;


    @PostMapping("/process")
    public void processRequestSchoolAdmin(@RequestParam int days) {
        requestSchoolAdminService.processRequestSchoolAdmin(days);
    }
}