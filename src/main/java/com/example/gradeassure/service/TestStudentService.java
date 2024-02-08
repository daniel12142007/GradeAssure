package com.example.gradeassure.service;

import com.example.gradeassure.dto.response.OptionsResponse;
import com.example.gradeassure.dto.response.QuestionStudentResponse;
import com.example.gradeassure.dto.response.TakeTestStudentResponse;
import com.example.gradeassure.dto.response.TestForStudentResponse;
import com.example.gradeassure.model.enums.AnswerFormat;
import com.example.gradeassure.repository.*;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    private final AudioRepository audioRepository;
    private final VideoRepository videoRepository;

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
        TakeTestStudentResponse test = testStudentRepository.findByTestId(testName, email);
        List<QuestionStudentResponse> list = new ArrayList<>();
        for (QuestionStudentResponse question : questionStudentRepository.findByAllQuestionResponse(test.getTestId())) {
            if (question.getAnswerFormat() == AnswerFormat.OPTION) {
                OptionsResponse optionsResponse = optionsStudentRepository.checkOption(question.getId()).orElse(null);
                List<OptionsResponse> optionsResponses = optionsStudentRepository.findByAllOptions(
                        question.getId(),
                        optionsResponse != null ? optionsResponse.getLetter() : ""
                );
                if (optionsResponse != null)
                    optionsResponses.add(optionsResponse);
                question.setOption(optionsResponses);

                optionsResponses.sort(Comparator.comparing(OptionsResponse::getVariation));

            }
            if (question.getAnswerFormat() == AnswerFormat.VIDEO) {
                question.setVideo(videoRepository.video(question.getId()) != null ? videoRepository.video(question.getId()) : null);
            }
            if (question.getAnswerFormat() == AnswerFormat.AUDIO) {
                question.setAudio(audioRepository.audio(question.getId()) != null ? audioRepository.audio(question.getId()) : null);
            }
            list.add(question);
        }
        test.setQuestionStudentResponses(list);
        return test;
    }
}