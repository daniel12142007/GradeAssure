package com.example.gradeassure.api;

import com.example.gradeassure.service.TestStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/reports")
public class ReportApi {
    private final TestStudentService testStudentService;

    @GetMapping("look/pdf")
    public ResponseEntity<byte[]> lookPdf(@RequestParam String testName,
                                          @RequestParam String email) {
        return testStudentService.lookPdf(testName, email);
    }

    @GetMapping("download/pdf")
    public ResponseEntity<byte[]> downloadPdf(@RequestParam String testName,
                                              @RequestParam String email) {
        return testStudentService.downloadPdf(testName, email);
    }
}
