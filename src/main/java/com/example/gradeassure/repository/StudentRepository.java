package com.example.gradeassure.repository;

import com.example.gradeassure.dto.response.RequestStudentForAllResponse;
import com.example.gradeassure.dto.response.RequestTeacherForAllResponse;
import com.example.gradeassure.model.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("""
            select
            new com.example.gradeassure.dto.response.RequestStudentForAllResponse(request.id,request.days,request.testName,t.email)
            from Student t
            left join t.requestStudents request
            on request.answered = false and request.dateAnswered = null
            where t.blocked = false
            """)
    List<RequestStudentForAllResponse> findAllRequestStudent();
}
