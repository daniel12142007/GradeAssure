package com.example.gradeassure.model;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class SchoolAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String email;
    private boolean blocked;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "schoolAdmin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RequestSchoolAdmin> requestSchoolAdmins;
}
