package com.example.gradeassure.repository;

import com.example.gradeassure.dto.response.RequestStudentForAllResponse;
import com.example.gradeassure.dto.response.RequestTeacherForAllResponse;
import com.example.gradeassure.dto.response.StudentOneResponse;
import com.example.gradeassure.dto.response.UsersResponse;
import com.example.gradeassure.model.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmail(String email);

    @Query("select new com.example.gradeassure.dto.response.UsersResponse(student.id,student.fullName,student.email) from Student student order by student.id")
    List<UsersResponse> findAllStudents();

    @Query("""
            select
            new com.example.gradeassure.dto.response.StudentOneResponse(
            student.id,
            student.fullName,
            student.email,
            (select count(test)
            from student.testStudents test),
            (select count(succesfully)
            from student.testStudents succesfully
            where succesfully.status = 'YES'),
            (select count(retake)
            from student.testStudents retake
            where retake.status = 'NO')
            )
            from Student student
            where student.id = :id
            """)
    StudentOneResponse findByIdStudent(@Param(value = "id") Long id);
}