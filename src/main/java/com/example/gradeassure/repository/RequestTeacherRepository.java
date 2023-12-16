package com.example.gradeassure.repository;

import com.example.gradeassure.model.RequestTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface RequestTeacherRepository extends JpaRepository<RequestTeacher, Long> {
}
