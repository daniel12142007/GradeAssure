package com.example.gradeassure.dto.request;

import com.example.gradeassure.model.enums.Action;
import lombok.Data;


@Data
public class RequestTeacherRequest {
    private Action action;
    private int days;
    private String subject;

}
