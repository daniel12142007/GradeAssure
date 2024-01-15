package com.example.gradeassure.repository;

import com.example.gradeassure.model.RequestStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface RequestStudentRepository extends JpaRepository<RequestStudent, Long> {
    @Query("select r from RequestStudent r where r.dateAnswered is null ")
    List<RequestStudent> findAllRequestStudent();
}