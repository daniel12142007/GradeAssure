package com.example.gradeassure.dto.response;

import com.example.gradeassure.model.enums.TestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultResponse {
    private Long id;
    private String fullName;
    private LocalDateTime datePassing;
    private long point;
    private long maxPoint;
    private boolean checked;
    private TestStatus status;

    public ResultResponse(Long id,
                          String fullName,
                          LocalDateTime datePassing,
                          long maxPoint,
                          boolean checked,
                          TestStatus status,
                          long point
    ) {
        this.id = id;
        this.fullName = fullName;
        this.datePassing = datePassing;
        this.maxPoint = maxPoint;
        this.checked = checked;
        this.status = status;
        this.point = point;
    }
}