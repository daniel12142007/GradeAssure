package com.example.gradeassure.repository;

import com.example.gradeassure.dto.response.QuestionStudentResponse;
import com.example.gradeassure.model.QuestionStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface QuestionStudentRepository extends JpaRepository<QuestionStudent, Long> {
    @Query("""
            select
            new com.example.gradeassure.dto.response.QuestionStudentResponse(
            question.id,
            question.question,
            question.answerFormat
            )
            from TestStudent test
            join test.questionStudents question
            where test.id = :testId
            order by test.id
            """)
    List<QuestionStudentResponse> findByAllQuestionResponse(@Param("testId") Long testId);
}