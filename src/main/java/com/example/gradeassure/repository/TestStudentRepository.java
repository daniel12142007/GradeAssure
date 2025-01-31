package com.example.gradeassure.repository;

import com.example.gradeassure.dto.response.ResultResponse;
import com.example.gradeassure.dto.response.TakeTestStudentResponse;
import com.example.gradeassure.dto.response.TestForStudentResponse;
import com.example.gradeassure.dto.response.TestStudentResponse;
import com.example.gradeassure.model.TestStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface TestStudentRepository extends JpaRepository<TestStudent, Long> {
    @Query("""
            select
            new com.example.gradeassure.dto.response.TakeTestStudentResponse(
            test.id,
            test.name
            )
            from TestStudent test
            where test.name = :testName
            and test.student.email = :email
            and test.checked = false
            """)
    TakeTestStudentResponse findByTestId(@Param("testName") String testName,
                                         @Param("email") String email);

    @Query("""
            select
            new com.example.gradeassure.dto.response.ResultResponse(
                    test.id,
                    test.student.fullName,
                    test.dateCreated,
                    (select sum(quest.points)
                    from test.testTeacher testTeacher
                    join testTeacher.questionTeachers quest),
                     coalesce(case when test.status = 'UNDEFINED' then false else true end,true),
                    test.status,
                    (select
                     sum(question.points)
                     from TestStudent testStudent
                     join testStudent.questionStudents question
                     where testStudent.id = test.id)
                    )
                    from TestStudent test
                    join test.questionStudents questsion
                    where
                    test.name = :testName
                    and questsion.audio is not null
                    or questsion.video is not null
                    or questsion.optionsStudent is not null
                    group by test.id, test.student.fullName, test.dateCreated, test.status
                    ORDER BY test.id
                    """)
    List<ResultResponse> findAllResultTest(@Param("testName") String testName);

    @Query("""
            select
            new com.example.gradeassure.dto.response.ResultResponse(
                    test.id,
                    test.student.fullName,
                    test.dateCreated,
                    (select sum(quest.points)
                    from test.testTeacher testTeacher
                    join testTeacher.questionTeachers quest),
                    true,
                    test.status,
                    (select
                     sum(question.points)
                     from TestStudent testStudent
                     join testStudent.questionStudents question
                     where testStudent.id = test.id)
                    )
                    from TestStudent test
                    join test.questionStudents questsion
                    where
                    test.name = :testName
                    and not test.status = 'UNDEFINED'
                    and questsion.audio is not null
                    or questsion.video is not null
                    or questsion.optionsStudent is not null
                    group by test.id, test.student.fullName, test.dateCreated, test.status
                    ORDER BY test.id
                    """)
    List<ResultResponse> findAllResultTestForSchoolAdmin(@Param("testName") String testName);


    @Query("""
            select
            sum(question.points)
            from TestStudent testStudent
            join testStudent.questionStudents question
            where testStudent.id = :testId
            """)
    long sumPoint(@Param("testId") Long testId);

    @Query("""
            select
            count(question)
            from TestStudent testStudent
            join testStudent.questionStudents question
            where question.checked = false
            and testStudent.id = :testId
            """)
    long checkQuestion(@RequestParam(value = "testId") Long testId);

    @Query("""
            select
            new com.example.gradeassure.dto.response.TestStudentResponse(
            test.id,
            test.name,
            test.dateCreated,
            (select count(i) from TestTeacher testTeacher
                join testTeacher.requestStudents i where testTeacher.id = test.id)
            )
            from Student student
            join student.testStudents test
            where student.email = :email
            """)
    List<TestStudentResponse> findAllTestStudentForStudent(@Param(value = "email") String email);
}