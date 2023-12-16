package com.example.gradeassure.repository;

import com.example.gradeassure.model.QuestionStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface QuestionStudentRepository extends JpaRepository<QuestionStudent, Long> {
}
