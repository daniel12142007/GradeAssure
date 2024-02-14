package com.example.gradeassure.repository;

import com.example.gradeassure.dto.response.RequestTeacherForAllResponse;
import com.example.gradeassure.dto.response.TeacherOneResponse;
import com.example.gradeassure.dto.response.UsersResponse;
import com.example.gradeassure.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
            where t.blocked = false
            """)
    List<RequestTeacherForAllResponse> findAllRequestTeacher();

    @Query("select new com.example.gradeassure.dto.response.UsersResponse(teacher.id,teacher.fullName,teacher.email) from Teacher teacher order by teacher.id")
    List<UsersResponse> findAllTeacher();

    @Query("""
            select
            new com.example.gradeassure.dto.response.TeacherOneResponse(
            teacher.id,
            teacher.fullName,
            teacher.email,
            (select count(test)
            from teacher.testTeachers test)
            )
            from Teacher teacher
            where teacher.id = :id
            """)
    TeacherOneResponse findByIdTeacherOne(@Param(value = "id") Long id);
}