package com.example.gradeassure.repository;

import com.example.gradeassure.model.RequestSchoolAdmin;
import com.example.gradeassure.model.SchoolAdmin;
import com.example.gradeassure.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface RequestSchoolAdminRepository extends JpaRepository<RequestSchoolAdmin, Long> {

    Optional<RequestSchoolAdmin> findById(Long id);

    Optional<Long> countBySchoolAdminAndAnsweredFalse(SchoolAdmin schoolAdmin);

    @Query("select r from RequestSchoolAdmin r where r.dateDeadline = :currentDateTime")
    List<RequestSchoolAdmin> findBySchoolAdminBlockedFalseAndDateDeadlineBeforeAndAnsweredFalse(
            LocalDateTime currentDateTime
    );

    @Query("SELECT sa FROM SchoolAdmin sa WHERE sa.blocked = true")
    List<SchoolAdmin> findBlockedSchoolAdmins();
}

