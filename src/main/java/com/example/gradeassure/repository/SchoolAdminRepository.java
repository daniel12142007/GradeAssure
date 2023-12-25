package com.example.gradeassure.repository;

import com.example.gradeassure.model.SchoolAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface SchoolAdminRepository extends JpaRepository<SchoolAdmin, Long> {
    Optional<SchoolAdmin> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT sa FROM SchoolAdmin sa WHERE sa.blocked = false OR sa.blocked IS NULL")
    List<SchoolAdmin> findAllUnblockedSchoolAdmins();
}
