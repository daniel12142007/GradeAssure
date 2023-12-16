package com.example.gradeassure.repository;

import com.example.gradeassure.model.RequestStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface RequestStudentRepository extends JpaRepository<RequestStudent, Long> {
}
