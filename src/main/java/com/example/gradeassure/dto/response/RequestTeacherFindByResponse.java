package com.example.gradeassure.dto.response;

import com.example.gradeassure.model.enums.Action;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestTeacherFindByResponse {
    private Long id;
    private String email;
    private String fullName;
    private Action action = Action.CHECK;
    private List<String> testCheck;

    public RequestTeacherFindByResponse(Long id, String email, String fullName) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
    }
}