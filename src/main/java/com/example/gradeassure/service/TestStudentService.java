package com.example.gradeassure.service;

import com.example.gradeassure.dto.response.*;
import com.example.gradeassure.model.*;
import com.example.gradeassure.model.enums.AnswerFormat;
import com.example.gradeassure.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TestStudentService {
    private final TestStudentRepository testStudentRepository;
    private final QuestionStudentRepository questionStudentRepository;
    private final QuestionTeacherRepository questionTeacherRepository;
    private final OptionsStudentRepository optionsStudentRepository;
    private final TestTeacherRepository testTeacherRepository;
    private final OptionsTeacherRepository optionsTeacherRepository;
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

    public TakeTestStudentResponse passingOption(String email,
                                                 String testName,
                                                 Long optionId,
                                                 Long questionId) {
        QuestionStudent questionStudent = questionStudentRepository.findById(questionId).orElse(null);
        if (questionStudent == null)
            throw new RuntimeException("not found question student");
        check(questionStudent);
        OptionsTeacher optionsTeacher = optionsTeacherRepository.findById(optionId).orElseThrow(RuntimeException::new);
        OptionsStudent optionsStudent = OptionsStudent.builder()
                .option(optionsTeacher.getOption())
                .correct(optionsTeacher.getCorrect())
                .letter(optionsTeacher.getLetter())
                .student(questionStudent)
                .build();
        questionStudent.setPoints(optionsTeacher.getCorrect() ?
                questionTeacherRepository.findByIdQuestionTeacher(questionStudent.getId()) : 0);
        optionsStudentRepository.save(optionsStudent);
        questionStudentRepository.save(questionStudent);
        return takeTestStudent(email, testName);
    }

    public TakeTestStudentResponse passingVideo(String email, String testName, String video, Long questionId) {
        QuestionStudent questionStudent = questionStudentRepository.findById(questionId).orElse(null);
        if (questionStudent == null)
            throw new RuntimeException("not found question student");
        check(questionStudent);
        Video video1 = new Video();
        video1.setVideo(video);
        video1.setAnswerVideo(questionStudent);
        videoRepository.save(video1);
        return takeTestStudent(email, testName);
    }

    public TakeTestStudentResponse passingAudio(String email, String testName, String audio, Long questionId) {
        QuestionStudent questionStudent = questionStudentRepository.findById(questionId).orElse(null);
        if (questionStudent == null)
            throw new RuntimeException("not found question student");
        check(questionStudent);
        Audio audio1 = new Audio();
        audio1.setAudio(audio);
        audio1.setAnswerAudio(questionStudent);
        audioRepository.save(audio1);
        return takeTestStudent(email, testName);
    }

    private void check(QuestionStudent questionStudent) {
        if (questionStudent.getOptionsStudent() != null && questionStudent.getVideo() != null && questionStudent.getAudio() != null)
            throw new RuntimeException("You already answer");
    }

    public List<ResultResponse> findAllResultTest(String testName, String email) {
        if (!testTeacherRepository.checkTeacher(email))
            throw new RuntimeException("You do not have access to this test");
        return testStudentRepository.findAllResultTest(testName);
    }
}