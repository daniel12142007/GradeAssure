package com.example.gradeassure.service;

import com.example.gradeassure.dto.response.RequestTeacherForAllResponse;
import com.example.gradeassure.dto.response.RequestTeacherResponse;
import com.example.gradeassure.model.RequestTeacher;
import com.example.gradeassure.model.Teacher;
import com.example.gradeassure.model.enums.Action;
import com.example.gradeassure.repository.RequestTeacherRepository;
import com.example.gradeassure.repository.TeacherRepository;
import com.example.gradeassure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestTeacherService {
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final RequestTeacherRepository requestTeacherRepository;

    public RequestTeacherResponse sendRequestCreate(String email, String subject, int days) {
        Teacher teacher = teacherRepository.findByEmail(email).orElseThrow(RuntimeException::new);
        RequestTeacher requestTeacher = RequestTeacher.builder()
                .days(days)
                .subject(subject)
                .teacher(teacher)
                .action(Action.CREATE)
                .dateCreated(LocalDateTime.now())
                .build();
        requestTeacherRepository.save(requestTeacher);
        return RequestTeacherResponse.builder()
                .id(requestTeacher.getId())
                .days(days)
                .subject(subject)
                .action(Action.CREATE)
                .dateCreated(requestTeacher.getDateCreated())
                .build();
    }

    public List<RequestTeacherForAllResponse> findAllRequest() {
        return teacherRepository.findAllRequestTeacher();
    }
}