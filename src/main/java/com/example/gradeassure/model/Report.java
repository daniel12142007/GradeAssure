package com.example.gradeassure.model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "report", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private TestTeacher testTeacher;
}
