package com.example.gradeassure.repository;

import com.example.gradeassure.dto.response.TakeTestStudentResponse;
import com.example.gradeassure.model.TestStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface TestStudentRepository extends JpaRepository<TestStudent, Long> {
//    @Query("""
//             select
//            new com.example.gradeassure.dto.response.TakeTestStudentResponse(
//            test.id,
//            test.name
//            )
//            from TestStudent test
//            where test.id = :testId
//            """)
//    List<TakeTestStudentResponse> findByTestId(@Param("testId") Long testId);
//private Long testId;
//    private String name;
//    private List<QuestionStudentResponse> questionStudentResponses;

// private Long id;
//    private String question;
//    private String video;
//    private String audio;
//    private AnswerFormat answerFormat;
//    private List<OptionsResponse> option;

//    private Long id;
//    private String variation;
//    private boolean chose;
}
