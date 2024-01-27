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
            coalesce(case when request.dateDeadline > current_date then true else false end, false),
            test.name,
            test.dateCreated,
            count(request)
            )
            from TestTeacher test
            left join test.requestStudents request
             where request.answered = true
                    and request.student.email = :email
                    group by test.id, test.name, test.dateCreated, request.dateDeadline
            """)
    List<TestForStudentResponse> findAllTestResponseForStudent(
            @Param(value = "email") String email);
}