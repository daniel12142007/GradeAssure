package com.example.gradeassure.service;

import com.example.gradeassure.dto.response.BlockedSchoolAdminResponse;
import com.example.gradeassure.model.Teacher;
import com.example.gradeassure.model.User;
import com.example.gradeassure.model.enums.Role;
import com.example.gradeassure.repository.SchoolAdminRepository;
import com.example.gradeassure.repository.TeacherRepository;
import com.example.gradeassure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final SchoolAdminRepository schoolAdminRepository;
    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;

    @Transactional
    public String deleteTeachers(List<Long> teacherIds) {
        teacherRepository.findAllById(teacherIds)
                .forEach(teacher -> {
                    teacher.getRequestTeachers().forEach(requestTeacher -> requestTeacher.setTeacher(null));
                    User user = teacher.getUser();
                    if (user != null) {
                        user.setTeacher(null);
                        userRepository.deleteById(user.getId());
                    }
                });
        teacherRepository.deleteInBatch(teacherRepository.findAllById(teacherIds));
        return "Учителя успешно удалены.";
    }

    public List<BlockedSchoolAdminResponse> getBlockedTeachersByIds(List<Long> teacherIds) {
        List<BlockedSchoolAdminResponse> blockedTeacherResponses = teacherRepository.findAllById(teacherIds).stream()
                .filter(Teacher::isBlocked)
                .map(teacher -> {
                    teacher.setBlocked(false);
                    teacherRepository.save(teacher);
                    User user = userRepository.findByEmail(teacher.getEmail()).orElseThrow();
                    user.setRole(Role.TEACHER);
                    userRepository.save(user);
                    BlockedSchoolAdminResponse response = new BlockedSchoolAdminResponse();
                    response.setId(teacher.getId());
                    response.setFullName(teacher.getFullName());
                    response.setEmail(teacher.getEmail());
                    return response;
                })
                .collect(Collectors.toList());

        return blockedTeacherResponses;
    }

}