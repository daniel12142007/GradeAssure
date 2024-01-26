package com.example.gradeassure.service;

import com.example.gradeassure.dto.response.RequestStudentFindAllResponse;
import com.example.gradeassure.dto.response.RequestStudentResponse;
import com.example.gradeassure.model.RequestStudent;
import com.example.gradeassure.model.Student;
import com.example.gradeassure.model.TestTeacher;
import com.example.gradeassure.repository.RequestStudentRepository;
import com.example.gradeassure.repository.StudentRepository;
import com.example.gradeassure.repository.TestTeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RequestStudentService {
    private final StudentRepository studentRepository;
    private final TestTeacherRepository teacherRepository;
    private final RequestStudentRepository requestStudentRepository;

    public RequestStudentResponse studentRequest(String email, int day, String testName) {
        Student student = studentRepository.findByEmail(email).orElseThrow();
        TestTeacher teacher = teacherRepository.findByName(testName);
        RequestStudent requestStudent = new RequestStudent();
        requestStudent.setDays(day);
        requestStudent.setStudent(student);
        requestStudent.setDateCreated(LocalDateTime.now());
        requestStudent.setTeacher(teacher);
        requestStudent.setTestName(testName);
        requestStudentRepository.save(requestStudent);
        RequestStudentResponse response = new RequestStudentResponse();
        response.setId(requestStudent.getId());
        response.setDays(requestStudent.getDays());
        response.setTestName(requestStudent.getTestName());
        response.setDateCreated(requestStudent.getDateCreated());
        return response;
    }


    public List<RequestStudentFindAllResponse> findAllRequestStudent() {
        List<RequestStudentFindAllResponse> responses = requestStudentRepository.findAllRequestStudent().stream()
                .map(request -> {
                    RequestStudentFindAllResponse response = new RequestStudentFindAllResponse();
                    response.setId(request.getId());
                    response.setEmail(request.getStudent().getEmail());
                    response.setDays(request.getDays());
                    response.setTestName(request.getTestName());
                    return response;
                })
                .collect(Collectors.toList());

        return responses;
    }


}