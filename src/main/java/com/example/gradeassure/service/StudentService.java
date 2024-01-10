package com.example.gradeassure.service;

import com.example.gradeassure.dto.response.BlockedSchoolAdminResponse;
import com.example.gradeassure.model.Student;
import com.example.gradeassure.model.User;
import com.example.gradeassure.model.enums.Role;
import com.example.gradeassure.repository.StudentRepository;
import com.example.gradeassure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    @Transactional
    public String deleteStudents(List<Long> studentIds) {
        studentRepository.findAllById(studentIds)
                .forEach(student -> {
                    student.getTestStudents().forEach(requestStudent -> requestStudent.setStudent(null));
                    User user = student.getUser();
                    if (user != null) {
                        user.setStudent(null);
                        userRepository.deleteById(user.getId());
                    }
                });

        studentRepository.deleteInBatch(studentRepository.findAllById(studentIds));

        return "Ученики успешно удалены.";
    }
    public List<BlockedSchoolAdminResponse> getBlockedStudentsByIds(List<Long> studentIds) {
        List<BlockedSchoolAdminResponse> blockedStudentResponses = studentRepository.findAllById(studentIds).stream()
                .filter(Student::isBlocked)
                .map(student -> {
                    student.setBlocked(false);
                    studentRepository.save(student);
                    User user = userRepository.findByEmail(student.getEmail()).orElseThrow();
                    user.setRole(Role.STUDENT);
                    userRepository.save(user);
                    BlockedSchoolAdminResponse response = new BlockedSchoolAdminResponse();
                    response.setId(student.getId());
                    response.setFullName(student.getFullName());
                    response.setEmail(student.getEmail());
                    return response;
                })
                .collect(Collectors.toList());

        return blockedStudentResponses;
    }
}


