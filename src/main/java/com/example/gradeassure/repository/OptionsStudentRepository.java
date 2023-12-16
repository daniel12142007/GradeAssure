package com.example.gradeassure.repository;

import com.example.gradeassure.model.OptionsStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface OptionsStudentRepository extends JpaRepository<OptionsStudent, Long> {
}
