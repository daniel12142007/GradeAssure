package com.example.gradeassure;

import com.example.gradeassure.model.User;
import com.example.gradeassure.model.enums.Role;
import com.example.gradeassure.repository.SchoolAdminRepository;
import com.example.gradeassure.repository.StudentRepository;
import com.example.gradeassure.repository.TeacherRepository;
import com.example.gradeassure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

@SpringBootApplication
@RequiredArgsConstructor
public class GradeAssureApplication {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final SchoolAdminRepository adminRepository;

    public static void main(String[] args) {
        SpringApplication.run(GradeAssureApplication.class, args);
    }

    @PostConstruct
    public void init() {
        User user = new User();
        user.setEmail("admin@gmail.com");
        user.setPassword(passwordEncoder.encode("Daniel124.!2"));
        user.setRole(Role.ADMIN);
        userRepository.save(user);
    }
}