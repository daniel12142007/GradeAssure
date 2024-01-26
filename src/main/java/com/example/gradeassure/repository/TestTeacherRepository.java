package com.example.gradeassure.repository;

import com.example.gradeassure.dto.response.TestForStudentResponse;
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
            coalesce(case when request.dateDeadline < current date() then true else false end, false),
            test.name,
            test.dateCreated,
            count(request)
            )
            from TestTeacher test
            join test.requestStudent request
            where request.answered = true
            """)
    List<TestForStudentResponse> findAllTestResponseForStudent(
            @Param(value = "email") String email);
}