package com.example.gradeassure.service;

import com.example.gradeassure.dto.response.BlockedSchoolAdminResponse;
import com.example.gradeassure.model.SchoolAdmin;
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

@RequiredArgsConstructor
@Service
public class SchoolAdminService {

}
