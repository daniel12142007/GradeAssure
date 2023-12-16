package com.example.gradeassure.repository;

import com.example.gradeassure.model.TestTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface TestTeacherRepository extends JpaRepository<TestTeacher, Long> {
}
