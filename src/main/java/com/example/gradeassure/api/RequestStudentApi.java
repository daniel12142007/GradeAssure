package com.example.gradeassure.api;

import com.example.gradeassure.dto.response.RequestStudentFindAllResponse;
import com.example.gradeassure.dto.response.RequestStudentResponse;
import com.example.gradeassure.service.RequestStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/requestStudent")
public class RequestStudentApi {

    private final RequestStudentService requestStudentService;

    @PostMapping("/requestStudent")
    public RequestStudentResponse requestStudentResponse(@RequestParam String email, @RequestParam int days, @RequestParam String testName) {
        return requestStudentService.studentRequest(email,days,testName);
    }
    @GetMapping("/findAllStudentRequests")
    public List <RequestStudentFindAllResponse> response(){
        return requestStudentService.findAllRequestStudent();
    }

}
