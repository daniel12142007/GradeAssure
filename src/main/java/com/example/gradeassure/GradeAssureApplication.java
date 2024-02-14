package com.example.gradeassure;

import com.example.gradeassure.api.RequestTeacherApi;
import com.example.gradeassure.model.*;
import com.example.gradeassure.model.enums.AnswerFormat;
import com.example.gradeassure.model.enums.Role;
import com.example.gradeassure.repository.*;
import com.example.gradeassure.service.RequestTeacherService;
import com.example.gradeassure.service.SchoolAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@SpringBootApplication
@RequiredArgsConstructor
@Transactional
public class GradeAssureApplication {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final TestTeacherRepository testTeacherRepository;
    private final SchoolAdminRepository adminRepository;
    private final QuestionTeacherRepository questionTeacherRepository;
    private final OptionsTeacherRepository optionsTeacherRepository;
    private final SchoolAdminService schoolAdminService;
    private final RequestStudentRepository requestStudentRepository;
    private final RequestTeacherService requestTeacherService;
    private final RequestTeacherApi requestTeacherApi;

    public static void main(String[] args) {
        SpringApplication.run(GradeAssureApplication.class, args);
    }

    @PostConstruct
    public void init() {
//        User user = User.builder()
//                .fullName("Student")
//                .role(Role.STUDENT)
//                .email("student@gmail.com")
//                .password(passwordEncoder.encode("123"))
//                .build();
//        Student student = Student.builder()
//                .fullName("Student")
//                .email("student@gmail.com")
//                .blocked(false)
//                .user(user)
//                .build();
//        userRepository.save(user);
//        studentRepository.save(student);
//
//        User user1 = User.builder()
//                .fullName("Teacher")
//                .role(Role.TEACHER)
//                .email("teacher@gmail.com")
//                .password(passwordEncoder.encode("123"))
//                .build();
//
//        Teacher teacher = Teacher.builder()
//                .fullName("Teacher")
//                .email("teacher@gmail.com")
//                .blocked(false)
//                .user(user1)
//                .build();
//
//        TestTeacher testTeacher = TestTeacher.builder()
//                .teacher(teacher)
//                .minScores(100)
//                .subject("Informatica")
//                .dateCreated(LocalDateTime.now())
//                .name("Java 1 A")
//                .build();
//
//        QuestionTeacher questionTeacher = QuestionTeacher.builder()
//                .question("1")
//                .testTeacher(testTeacher)
//                .answerFormat(AnswerFormat.OPTION)
//                .dateCreated(LocalDateTime.now())
//                .numberOption(2)
//                .points(10)
//                .build();
//
//        OptionsTeacher optionsTeacher = OptionsTeacher.builder()
//                .teacher(questionTeacher)
//                .option("A")
//                .correct(false)
//                .letter("My names Daniel")
//                .build();
//
//        OptionsTeacher optionsTeacher1 = OptionsTeacher.builder()
//                .teacher(questionTeacher)
//                .option("B")
//                .correct(true)
//                .letter("My names Daniel_tamoe")
//                .build();
//
//        QuestionTeacher questionTeacher1 = QuestionTeacher.builder()
//                .question("2")
//                .testTeacher(testTeacher)
//                .answerFormat(AnswerFormat.AUDIO)
//                .dateCreated(LocalDateTime.now())
//                .points(10)
//                .build();
//
//        QuestionTeacher questionTeacher2 = QuestionTeacher.builder()
//                .question("3")
//                .testTeacher(testTeacher)
//                .answerFormat(AnswerFormat.VIDEO)
//                .dateCreated(LocalDateTime.now())
//                .points(10)
//                .build();
//
//
//        userRepository.save(user1);
//        teacherRepository.save(teacher);
//        testTeacherRepository.save(testTeacher);
//        questionTeacherRepository.save(questionTeacher);
//        questionTeacherRepository.save(questionTeacher1);
//        questionTeacherRepository.save(questionTeacher2);
//        optionsTeacherRepository.save(optionsTeacher);
//        optionsTeacherRepository.save(optionsTeacher1);
//
//        User user2 = User.builder()
//                .fullName("Admin School")
//                .role(Role.ADMINSCHOOL)
//                .email("adminschool@gmail.com")
//                .password(passwordEncoder.encode("123"))
//                .build();
//
//        SchoolAdmin admin = SchoolAdmin.builder()
//                .fullName("Admin School")
//                .email("adminschool@gmail.com")
//                .blocked(false)
//                .user(user2)
//                .build();
//
//        userRepository.save(user2);
//        adminRepository.save(admin);
//
//        RequestStudent requestStudent = RequestStudent.builder()
//                .testName("Java 1 A")
//                .student(student)
//                .days(11)
//                .dateCreated(LocalDateTime.now())
//                .teacher(testTeacher)
//                .build();
//        requestStudentRepository.save(requestStudent);
//        schoolAdminService.allowById(1L);
//
//        User user5 = User.builder()
//                .fullName("Student Answer")
//                .role(Role.STUDENT)
//                .email("student1@gmail.com")
//                .password(passwordEncoder.encode("123"))
//                .build();
//        Student student5 = Student.builder()
//                .fullName("Student Answer")
//                .email("student1@gmail.com")
//                .blocked(false)
//                .user(user5)
//                .build();
//        userRepository.save(user5);
//        studentRepository.save(student5);
//
//        RequestStudent requestStudent1 = RequestStudent.builder()
//                .testName("Java 1 A")
//                .student(student5)
//                .days(11)
//                .dateCreated(LocalDateTime.now())
//                .teacher(testTeacher)
//                .build();
//        requestStudentRepository.save(requestStudent1);
//        schoolAdminService.allowById(2L);

    }
}