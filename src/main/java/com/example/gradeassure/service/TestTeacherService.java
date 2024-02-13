package com.example.gradeassure.service;

import com.example.gradeassure.dto.request.OptionsTeacherRequest;
import com.example.gradeassure.dto.request.QuestionTeacherRequest;
import com.example.gradeassure.dto.response.*;
import com.example.gradeassure.model.*;
import com.example.gradeassure.model.enums.AnswerFormat;
import com.example.gradeassure.model.enums.TestStatus;
import com.example.gradeassure.repository.*;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TestTeacherService {
    private final TestTeacherRepository testTeacherRepository;
    private final TeacherRepository teacherRepository;
    private final TestStudentService testStudentService;
    private final TestStudentRepository testStudentRepository;
    private final QuestionTeacherRepository questionTeacherRepository;
    private final QuestionStudentRepository questionStudentRepository;
    private final RequestTeacherRepository requestTeacherRepository;
    private final OptionsTeacherRepository optionsTeacherRepository;

    public TestTeacherResponse createTestTeacher(String email, String testName) {
        Teacher teacher = teacherRepository.findByEmail(email).orElseThrow(RuntimeException::new);
        RequestTeacher requestTeacher = requestTeacherRepository.findRequestByTeacherId(email);
        requestTeacher.setAnswered(false);
        if (testTeacherRepository.existsByName(testName))
            throw new RuntimeException("Такое название теста уже существует");
        if (requestTeacher.getTestTeacher() != null)
            throw new RuntimeException("Teacher уже создал тест");
        TestTeacher testTeacher = TestTeacher.builder()
                .name(testName)
                .teacher(teacher)
                .create(requestTeacher)
                .subject(requestTeacher.getSubject())
                .dateCreated(LocalDateTime.now())
                .build();
        requestTeacherRepository.save(requestTeacher);
        testTeacherRepository.save(testTeacher);
        return TestTeacherResponse.builder()
                .id(testTeacher.getId())
                .name(testTeacher.getName())
                .subject(testTeacher.getSubject())
                .dateCreated(testTeacher.getDateCreated())
                .build();
    }

    public List<QuestionTeacherResponse> createQuestion(QuestionTeacherRequest request, Long testId) {
        TestTeacher testTeacher = testTeacherRepository.findById(testId).orElseThrow(RuntimeException::new);
        QuestionTeacher questionTeacher = QuestionTeacher.builder()
                .testTeacher(testTeacher)
                .points(request.getPoints())
                .question(request.getQuestion())
                .answerFormat(request.getAnswerFormat())
                .numberOption(request.getNumberOption())
                .dateCreated(LocalDateTime.now())
                .build();
        questionTeacherRepository.save(questionTeacher);
        return questionTeacherRepository.findAllQuestionResponse(testId).stream().map(
                questionTeacherResponse -> {
                    questionTeacherResponse.setOptionsTeacherResponses(optionsTeacherRepository.findAllOptionsTeacherResponse(questionTeacherResponse.getId()));
                    return questionTeacherResponse;
                }
        ).toList();
    }

    public QuestionTeacherResponse createOption(OptionsTeacherRequest request, Long questionId) {
        QuestionTeacher questionTeacher = questionTeacherRepository.findById(questionId).orElseThrow(RuntimeException::new);
        if (optionsTeacherRepository.optionCount(questionId) + 1 > questionTeacher.getNumberOption())
            throw new RuntimeException("Вариантов больше чем указано");
        OptionsTeacher optionsTeacher = OptionsTeacher.builder()
                .correct(request.getCorrect())
                .letter(request.getLetter())
                .teacher(questionTeacher)
                .option(request.getOption())
                .build();
        optionsTeacherRepository.save(optionsTeacher);
        QuestionTeacherResponse questionTeacherResponse =
                questionTeacherRepository.findByIdQuestionResponse(questionTeacher.getId());
        questionTeacherResponse.setOptionsTeacherResponses(optionsTeacherRepository.findAllOptionsTeacherResponse(questionId));
        return questionTeacherResponse;
    }

    public TestTeacherResponse minScores(Long testId, int scores) {
        TestTeacher testTeacher = testTeacherRepository.findById(testId).orElseThrow(RuntimeException::new);
        testTeacher.setMinScores(scores);
        testTeacherRepository.save(testTeacher);
        return findById(testId);
    }

    public TestTeacherResponse findById(Long testId) {
        TestTeacher testTeacher = testTeacherRepository.findById(testId).orElseThrow(RuntimeException::new);
        return TestTeacherResponse.builder()
                .minScores(testTeacher.getMinScores())
                .id(testTeacher.getId())
                .name(testTeacher.getName())
                .subject(testTeacher.getSubject())
                .dateCreated(testTeacher.getDateCreated())
                .questionCount(questionTeacherRepository.findCountQuestionByIdTest(testId))
                .build();
    }

    public QuestionTeacherResponse findByIdQuestion(Long questionId) {
        QuestionTeacherResponse questionTeacherResponse =
                questionTeacherRepository.findByIdQuestionResponse(questionId);
        questionTeacherResponse.setOptionsTeacherResponses(optionsTeacherRepository.findAllOptionsTeacherResponse(questionId));
        return questionTeacherResponse;
    }

    public List<QuestionTeacherResponse> findAllQuestion(Long testId) {
        return questionTeacherRepository.findAllQuestionResponse(testId).stream().map(
                questionTeacherResponse -> {
                    questionTeacherResponse.setOptionsTeacherResponses(optionsTeacherRepository.findAllOptionsTeacherResponse(questionTeacherResponse.getId()));
                    return questionTeacherResponse;
                }
        ).toList();
    }

    //    TODO: check test
    public List<TestForStudentResponse> findAllTestForTeacher(String email) {
        return testTeacherRepository.findAllTestResponseForTeacher(email);
    }

    public CheckTestStudentResponse checkVideo(Long testId, String email, int point, Long questionId) {
        QuestionStudent questionStudent = questionStudentRepository.findById(questionId).orElseThrow();
        QuestionTeacher questionTeacher = questionStudent.getQuestionTeacher();
        if (!testTeacherRepository.checkTeacher(email))
            throw new RuntimeException("You do not have access to this test");
        if (point > questionTeacher.getPoints())
            throw new RuntimeException("Вы дали слишком много баллов максимум:" + questionTeacher.getPoints());
        if (questionStudent.getAnswerFormat() != AnswerFormat.VIDEO)
            throw new RuntimeException("У этого вопроса формат должно быть видио");
        questionStudent.setChecked(true);
        questionStudent.setPoints(point);
        questionStudentRepository.save(questionStudent);
        return testStudentService.findByIdTestStudentCheck(testId, email);
    }

    public CheckTestStudentResponse checkAudio(Long testId, String email, int point, Long questionId) {
        QuestionStudent questionStudent = questionStudentRepository.findById(questionId).orElseThrow();
        QuestionTeacher questionTeacher = questionStudent.getQuestionTeacher();
        if (!testTeacherRepository.checkTeacher(email))
            throw new RuntimeException("You do not have access to this test");
        if (point > questionTeacher.getPoints())
            throw new RuntimeException("Вы дали слишком много баллов максимум:" + questionTeacher.getPoints());
        if (questionStudent.getAnswerFormat() != AnswerFormat.AUDIO)
            throw new RuntimeException("У этого вопроса формат должно быть аудио");
        questionStudent.setChecked(true);
        questionStudent.setPoints(point);
        questionStudentRepository.save(questionStudent);
        return testStudentService.findByIdTestStudentCheck(testId, email);
    }

    public List<ResultResponse> finishCheck(Long testId, String email) {
        TestStudent testStudent = testStudentRepository.findById(testId).orElseThrow();
        TestTeacher testTeacher = testStudent.getTestTeacher();
        if (!testTeacherRepository.checkTeacher(email))
            throw new RuntimeException("You do not have access to this test");
        if (testStudentRepository.sumPoint(testId) >= testTeacher.getMinScores())
            testStudent.setStatus(TestStatus.YES);
        if (testStudentRepository.sumPoint(testId) < testTeacher.getMinScores())
            testStudent.setStatus(TestStatus.NO);
        if (testStudentRepository.checkQuestion(testStudent.getId()) > 0)
            throw new RuntimeException("Вы проверили не все ответы");
        testStudentRepository.save(testStudent);
        return testStudentService.findAllResultTest(testStudent.getName(), email);
    }
}