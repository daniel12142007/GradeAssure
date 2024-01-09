package com.example.gradeassure.repository;

import com.example.gradeassure.model.RequestTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface RequestTeacherRepository extends JpaRepository<RequestTeacher, Long> {
    @Query("""
            select r
            from RequestTeacher r
            where r.teacher.id in (:id)
            and r.dateAnswered is null
            """)
    List<RequestTeacher> findAllRequestByTeacherId(@Param(value = "id") List<Long> id);

    @Query("""
            select r
            from RequestTeacher r
            where r.teacher.id = :id
            and r.dateAnswered is null
            """)
    List<RequestTeacher> findRequestByTeacherId(@Param(value = "id") Long id);

    @Query("select r from RequestTeacher r where r.teacher.id = :id and r.action = 0 and r.dateAnswered = null")
    RequestTeacher findRequestCreate(@Param(value = "id") Long id);
}