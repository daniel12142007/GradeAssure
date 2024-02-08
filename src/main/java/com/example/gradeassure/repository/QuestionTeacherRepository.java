package com.example.gradeassure.repository;

import com.example.gradeassure.dto.response.QuestionTeacherResponse;
import com.example.gradeassure.model.OptionsTeacher;
import com.example.gradeassure.model.QuestionTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface QuestionTeacherRepository extends JpaRepository<QuestionTeacher, Long> {
    @Query("""
            select
            new com.example.gradeassure.dto.response.QuestionTeacherResponse(q.id,q.question,q.answerFormat,q.points,q.numberOption)
            from QuestionTeacher q
            where q.testTeacher.id = :testID
            """)
    List<QuestionTeacherResponse> findAllQuestionResponse(@Param("testID") Long testID);

    @Query("""
            select
            new com.example.gradeassure.dto.response.QuestionTeacherResponse(q.id,q.question,q.answerFormat,q.points,q.numberOption)
            from QuestionTeacher q
            where q.id = :questionId
            """)
    QuestionTeacherResponse findByIdQuestionResponse(@Param("questionId") Long questionId);

    @Query("""
            select
            count(q)
            from QuestionTeacher q
            where q.testTeacher.id = :testId
            """)
    int findCountQuestionByIdTest(@Param("testId") Long testId);

    @Query("""
            select question
            from QuestionTeacher question
            where question.testTeacher.id = :id
            order by question.id
            """)
    List<QuestionTeacher> findAllQuestionTeacher(@Param(value = "id") Long id);
}