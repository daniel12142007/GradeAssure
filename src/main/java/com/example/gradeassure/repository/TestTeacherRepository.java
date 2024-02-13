package com.example.gradeassure.repository;

import com.example.gradeassure.dto.response.*;
import com.example.gradeassure.model.TestTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface TestTeacherRepository extends JpaRepository<TestTeacher, Long> {
    boolean existsByName(String name);

    TestTeacher findByName(String name);


    @Query("""
            select
            new com.example.gradeassure.dto.response.TestForStudentResponse(
            test.id,
            coalesce(case when request.dateDeadline > current_date then true else false end, false),
            test.name,
            test.dateCreated,
            (select count(i) from TestTeacher testTeacher join testTeacher.requestStudents i where testTeacher.id = test.id)
            )
            from TestTeacher test
            left join test.requestStudents request
            on request.answered = true and request.student.email = :email
            """)
    List<TestForStudentResponse> findAllTestResponseForStudent(
            @Param(value = "email") String email);

    @Query("""
            select
            new com.example.gradeassure.dto.response.TestForStudentResponse(
            test.id,
            coalesce(case when request.dateDeadline > current_date then true else false end, false),
            test.name,
            test.dateCreated,
            (select count(i) from TestTeacher testTeacher
            join testTeacher.requestStudents i where testTeacher.id = test.id)
            )
            from TestTeacher test
            left join test.check request
            on request.answered = true and request.teacher.email = :email
            """)
    List<TestForStudentResponse> findAllTestResponseForTeacher(
            @Param(value = "email") String email);

    @Query("""
            select
            coalesce(case when request.dateDeadline > current_date then true else false end, false)
            from TestTeacher test
            left join test.check request
            on request.answered = true and request.teacher.email = :email
            """)
    boolean checkTeacher(@Param("email") String email);

    @Query("""
            select
            new com.example.gradeassure.dto.response.CheckTestStudentResponse(
            testStudent.id
            )
            from TestTeacher testTeacher
            join testTeacher.testStudents testStudent
            where testStudent.id = :testId
            order by testStudent.id
            """)
    CheckTestStudentResponse findByIdCheckTestStudent(@Param(value = "testId") Long testId);

    @Query("""
            select
            new com.example.gradeassure.dto.response.CheckQuestionTeacherResponse(
                         questionStudent.id,
                         questionTeacher.question,
                         questionStudent.points,
                         questionTeacher.answerFormat
            )
            from TestStudent testStudent
            join testStudent.questionStudents questionStudent
            join questionStudent.questionTeacher questionTeacher
            where testStudent.id = :testId
            order by questionStudent.id
            """)
    List<CheckQuestionTeacherResponse> findByIdCheckQuestionTeacher(@Param(value = "testId") Long testId);


    @Query("""
            select
            new com.example.gradeassure.dto.response.CheckOptionResponse(
             optionTeacher.id,
             optionTeacher.option,
             optionTeacher.letter,
             optionTeacher.correct,
             coalesce(case when optionTeacher.letter = optionStudent.letter then true else false end ,false )
            )
            from QuestionStudent questionStudent
            left join questionStudent.questionTeacher questionTeacher
            left join questionTeacher.optionsTeachers optionTeacher
            left join questionStudent.optionsStudent optionStudent
            where questionStudent.id = :questionId
            order by optionTeacher.id
            """)
    List<CheckOptionResponse> findByIdCheckOption(@Param(value = "questionId") Long questionId);

    @Query("select video.video from Video video where video.answerVideo.id = :questionId")
    String video(@Param(value = "questionId") Long questionId);

    @Query("select audio.audio from Audio audio where audio.answerAudio.id = :questionId")
    String audio(@Param(value = "questionId") Long questionId);
}