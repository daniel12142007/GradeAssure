package com.example.gradeassure.repository;

import com.example.gradeassure.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface VideoRepository extends JpaRepository<Video,Long> {
    @Query("select v.video from Video v where v.answerVideo.id = :id")
    String video(@Param("id") Long id);
}