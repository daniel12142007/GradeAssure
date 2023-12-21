package com.example.gradeassure.mapper.edit;

import com.example.gradeassure.dto.request.TestTeacherRequest;
import com.example.gradeassure.model.OptionsTeacher;
import com.example.gradeassure.model.TestTeacher;
import org.springframework.stereotype.Component;

@Component
public class TestTeacherEdit {
    public TestTeacher create(TestTeacherRequest request) {
        TestTeacher testTeacher = new TestTeacher();
        testTeacher.setName(request.getName());
        testTeacher.setMinScores(request.getMinScores());

        return testTeacher;
    }
}
