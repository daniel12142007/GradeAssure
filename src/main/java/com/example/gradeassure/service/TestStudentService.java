package com.example.gradeassure.service;

import com.example.gradeassure.dto.response.TestForStudentResponse;
import com.example.gradeassure.repository.StudentRepository;
import com.example.gradeassure.repository.TestStudentRepository;
import com.example.gradeassure.repository.TestTeacherRepository;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TestStudentService {
    private final TestStudentRepository testStudentRepository;
    private final StudentRepository studentRepository;
    private final TestTeacherRepository testTeacherRepository;

    public List<TestForStudentResponse> findAllTestForStudent(String email) {
        if (email == null)
            throw new RuntimeException("Invalid format email");
        return testTeacherRepository.findAllTestResponseForStudent(email);
    }
}