package com.example.gradeassure.repository;

import com.example.gradeassure.dto.response.RequestTeacherForAllResponse;
import com.example.gradeassure.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("""
            select
            new com.example.gradeassure.dto.response.RequestTeacherForAllResponse(t.id,t.email,requestCreate.id,requestCheck.id)
            from Teacher t
            left join t.requestTeachers requestCreate
            on requestCreate.answered = false and requestCreate.dateAnswered = null and requestCreate.action = 0
            left join t.requestTeachers requestCheck
            on requestCheck.answered = false and requestCheck.dateAnswered = null and requestCheck.action = 1
            """)
    List<RequestTeacherForAllResponse> findAllRequestTeacher();
}