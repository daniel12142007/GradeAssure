package com.example.gradeassure.repository;

import com.example.gradeassure.model.Audio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface AudioRepository extends JpaRepository<Audio, Long> {
    @Query("select a.audio from Audio a where a.answerAudio.id = :id")
    String audio(@Param("id") Long id);
}