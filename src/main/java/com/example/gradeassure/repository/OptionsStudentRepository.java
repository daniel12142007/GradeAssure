package com.example.gradeassure.repository;

import com.example.gradeassure.dto.response.OptionsResponse;
import com.example.gradeassure.model.OptionsStudent;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface OptionsStudentRepository extends JpaRepository<OptionsStudent, Long> {
    @Query("""
            select
            new com.example.gradeassure.dto.response.OptionsResponse(
            option.id,
            option.option,
            option.letter,
            false
            )
            from QuestionStudent question
            join question.questionTeacher teacher
            left join teacher.optionsTeachers option
            where question.id = :questionId and not option.letter = :letter
            """)
    List<OptionsResponse> findByAllOptions(@Param(value = "questionId") Long questionId, @Param(value = "letter") String letter);

    @Query("""
            select
            new com.example.gradeassure.dto.response.OptionsResponse(
            option.id,
            option.option,
            option.letter,
            true
            )
            from QuestionStudent questionStudent
            join questionStudent.optionsStudent option
            where questionStudent.id = :questionId
            """)
    Optional<OptionsResponse> checkOption(@Param("questionId") Long questionId);
}