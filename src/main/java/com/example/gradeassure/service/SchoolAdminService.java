package com.example.gradeassure.service;

import com.example.gradeassure.dto.response.*;
import com.example.gradeassure.model.*;
import com.example.gradeassure.model.enums.Role;
import com.example.gradeassure.repository.RequestStudentRepository;
import com.example.gradeassure.repository.StudentRepository;
import com.example.gradeassure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class SchoolAdminService {
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final RequestStudentRepository requestStudentRepository;
    private final RequestStudentService requestStudentService;

    public List<RequestStudentFindAllResponse> refuseByIdAll(List<Long> studentId) {
        List<RequestStudent> list = requestStudentRepository.findAllStudentsRequestById(studentId).stream().map(
                requestStudent -> {
                    requestStudent.setDateAnswered(LocalDateTime.now());
                    requestStudent.setDateDeadline(LocalDateTime.now());
                    requestStudent.setAnswered(false);
                    return requestStudent;
                }
        ).toList();
        requestStudentRepository.saveAll(list);
        return requestStudentService.findAllRequestStudent();
    }

    public List<RequestStudentFindAllResponse> allowById(Long requestCreate) {
        RequestStudent requestStudent = requestStudentRepository.findById(requestCreate).orElseThrow(RuntimeException::new);
        requestStudent.setDateAnswered(LocalDateTime.now());
        requestStudent.setDateDeadline(LocalDateTime.now().plusDays(requestStudent.getDays()));
        requestStudent.setAnswered(true);
        Student student = requestStudent.getStudent();
        if (requestStudentRepository.allowStudent(student.getEmail())) {
            User user = student.getUser();
            user.setRole(Role.STUDENT);
            userRepository.save(user);
        }
        requestStudentRepository.save(requestStudent);
        return requestStudentService.findAllRequestStudent();
    }

    public List<RequestStudentFindAllResponse> blockStudent(List<Long> studentId) {
        List<RequestStudent> list = requestStudentRepository.findAllStudentsRequestById(studentId).stream().map(
                requestStudent -> {
                    requestStudent.setDateAnswered(LocalDateTime.now());
                    requestStudent.setDateDeadline(LocalDateTime.now());
                    requestStudent.setAnswered(false);
                    return requestStudent;
                }
        ).toList();
        requestStudentRepository.saveAll(list);
        studentRepository.saveAll(studentRepository.findAllById(studentId).stream().map(
                teacher -> {
                    teacher.setBlocked(true);
                    User user = teacher.getUser();
                    user.setRole(Role.BLOCKED);
                    userRepository.save(user);
                    return teacher;
                }
        ).toList());
        return requestStudentService.findAllRequestStudent();
    }
}