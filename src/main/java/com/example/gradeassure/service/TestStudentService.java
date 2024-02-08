package com.example.gradeassure.service;

import com.example.gradeassure.dto.response.TakeTestStudentResponse;
import com.example.gradeassure.dto.response.TestForStudentResponse;
import com.example.gradeassure.repository.*;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TestStudentService {
    private final TestStudentRepository testStudentRepository;
    private final QuestionStudentRepository questionStudentRepository;
    private final OptionsStudentRepository optionsStudentRepository;
    private final StudentRepository studentRepository;
    private final TestTeacherRepository testTeacherRepository;
    private final EntityManager entityManager;

    public List<TestForStudentResponse> findAllTestForStudent(String email) {
        if (email == null)
            throw new RuntimeException("Invalid format email");
        return testTeacherRepository.findAllTestResponseForStudent(email);
    }

    public long countTime(String email) {
        LocalDateTime dateAnswered = (LocalDateTime) entityManager.createQuery("SELECT r.dateAnswered FROM RequestStudent r WHERE r.student.email = :email AND r.answered = true")
                .setParameter("email", email)
                .getSingleResult();
        LocalDateTime dateDeadline = (LocalDateTime) entityManager.createQuery("SELECT r.dateDeadline FROM RequestStudent r WHERE r.student.email = :email AND r.answered = true")
                .setParameter("email", email)
                .getSingleResult();
        LocalDate localDateAnswered = dateAnswered.toLocalDate();
        LocalDate localDateDeadline = dateDeadline.toLocalDate();
        long daysDifference = ChronoUnit.DAYS.between(localDateAnswered, localDateDeadline);
        return Math.toIntExact(daysDifference);
    }

    public TakeTestStudentResponse takeTestStudent(String email, String testName) {
        return testStudentRepository.findByTestId(testName, email);
    }
}