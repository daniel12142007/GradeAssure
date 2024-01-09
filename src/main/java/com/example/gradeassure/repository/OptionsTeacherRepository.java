package com.example.gradeassure.repository;

import com.example.gradeassure.dto.response.OptionsTeacherResponse;
import com.example.gradeassure.model.OptionsTeacher;
import org.apache.catalina.LifecycleState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface OptionsTeacherRepository extends JpaRepository<OptionsTeacher, Long> {
    @Query("""
            select count(o)
            from OptionsTeacher o
            where o.teacher.id = :questionId
            """)
    int optionCount(@Param(value = "questionId") Long questionId);

    @Query("""
            select
            new com.example.gradeassure.dto.response.OptionsTeacherResponse(option.id,option.option,option.letter,option.correct)
            from OptionsTeacher option
            where option.teacher.id = :questionId
            """)
    List<OptionsTeacherResponse> findAllOptionsTeacherResponse(@Param(value = "questionId") Long questionId);
}