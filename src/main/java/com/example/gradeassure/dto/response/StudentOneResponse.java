package com.example.gradeassure.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class StudentOneResponse {
    private Long id;
    private String fullName;
    private String email;
    private long passingTest;
    private long succesfully;
    private long retake;
    private long percent;

    public StudentOneResponse(Long id,
                              String fullName,
                              String email,
                              long passingTest,
                              long succesfully,
                              long retake) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.passingTest = passingTest;
        this.succesfully = succesfully;
        this.retake = retake;
        if (retake == succesfully) {
            this.percent = 50;
        }
        System.out.println(retake);
        System.out.println(succesfully);
        if (retake != 0) {
            this.percent = Math.round(((float) succesfully / passingTest) * 100);
        } else {
            this.percent = 100;
        }
    }
}