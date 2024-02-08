package com.example.gradeassure.repository;

import com.example.gradeassure.dto.response.OptionsResponse;
import com.example.gradeassure.model.OptionsStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface OptionsStudentRepository extends JpaRepository<OptionsStudent, Long> {
//    @Query("""
//            select
//            new com.example.gradeassure.dto.response.OptionsResponse(
//
//            )
//            from OptionsTeacher option
//            join
//            """)
//    List<OptionsResponse> findByAllOptions();
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