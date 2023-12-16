package com.example.gradeassure.repository;

import com.example.gradeassure.model.Audio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface AudioRepository extends JpaRepository<Audio, Long> {

}
