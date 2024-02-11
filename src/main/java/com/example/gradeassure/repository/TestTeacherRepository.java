package com.example.gradeassure.repository;

import com.example.gradeassure.dto.response.TakeTestStudentResponse;
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
            (select count(i) from TestTeacher testTeacher join testTeacher.requestStudents i where testTeacher.id = test.id)
            )
            from TestTeacher test
            left join test.requestStudents request
            on request.answered = true and request.student.email = :email
            """)
    List<TestForStudentResponse> findAllTestResponseForStudent(
            @Param(value = "email") String email);

    @Query("""
            select
            new com.example.gradeassure.dto.response.TestForStudentResponse(
            test.id,
            coalesce(case when request.dateDeadline > current_date then true else false end, false),
            test.name,
            test.dateCreated,
            (select count(i) from TestTeacher testTeacher
            join testTeacher.requestStudents i where testTeacher.id = test.id)
            )
            from TestTeacher test
            left join test.check request
            on request.answered = true and request.teacher.email = :email
            """)
    List<TestForStudentResponse> findAllTestResponseForTeacher(
            @Param(value = "email") String email);
}