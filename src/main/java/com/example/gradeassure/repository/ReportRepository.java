package com.example.gradeassure.repository;

import com.example.gradeassure.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
}
