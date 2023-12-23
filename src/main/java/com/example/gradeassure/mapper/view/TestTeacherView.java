package com.example.gradeassure.mapper.view;

import com.example.gradeassure.dto.Response.TestTeacherResponse;
import com.example.gradeassure.model.TestTeacher;
import org.springframework.stereotype.Component;

@Component
public class TestTeacherView {
    public TestTeacherResponse map(TestTeacher testTeacher) {
        TestTeacherResponse teacherResponse = new TestTeacherResponse();
        teacherResponse.setId(testTeacher.getId());
        teacherResponse.setName(teacherResponse.getName());
        teacherResponse.setDateCreated(testTeacher.getDateCreated());
        teacherResponse.setMinScores(testTeacher.getMinScores());
        teacherResponse.setSubject(testTeacher.getSubject());

        return teacherResponse;
    }
}
