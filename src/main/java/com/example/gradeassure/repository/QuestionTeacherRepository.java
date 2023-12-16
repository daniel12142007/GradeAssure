package com.example.gradeassure.repository;

import com.example.gradeassure.model.QuestionTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface QuestionTeacherRepository extends JpaRepository<QuestionTeacher, Long> {
}
