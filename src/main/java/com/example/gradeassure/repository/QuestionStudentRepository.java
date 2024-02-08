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
//    @Query("""
//            select
//            new com.example.gradeassure.dto.response.QuestionStudentResponse(
//            question.id,
//            question.question,
//            question.answerFormat
//            )
//            from QuestionStudent question
//            where question.testStudent.id = :testId
//            order by question.id
//            """)
//    List<QuestionStudentResponse>findByAllQuestionResponse(@Param("testId")Long testId);
}
//    Long id, String question, AnswerFormat answerFormat

////private Long testId;
////    private String name;
////    private List<QuestionStudentResponse> questionStudentResponses;
//
//// private Long id;
////    private String question;
////    private String video;
////    private String audio;
////    private AnswerFormat answerFormat;
////    private List<OptionsResponse> option;
//
////    private Long id;
////    private String variation;
////    private boolean chose;