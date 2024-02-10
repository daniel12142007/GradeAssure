package com.example.gradeassure.repository;

import com.example.gradeassure.dto.response.RequestTeacherFindByResponse;
import com.example.gradeassure.model.RequestTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface RequestTeacherRepository extends JpaRepository<RequestTeacher, Long> {
    @Query("""
            select r
            from RequestTeacher r
            where r.teacher.id in (:id)
            and r.dateAnswered is null
            """)
    List<RequestTeacher> findAllRequestByTeacherId(@Param(value = "id") List<Long> id);

    @Query("""
            select r
            from RequestTeacher r
            where r.teacher.email = :email
            and r.answered = true
            and r.action = 0
            """)
    RequestTeacher findRequestByTeacherId(@Param(value = "email") String email);

    @Query("select r from RequestTeacher r where r.teacher.id = :id and r.action = 0 and r.dateAnswered = null")
    RequestTeacher findRequestCreate(@Param(value = "id") Long id);

    @Query("select r from RequestTeacher r where r.teacher.id = :id and r.action = 1 and r.dateAnswered = null")
    RequestTeacher findRequestCheck(@Param(value = "id") Long id);


    @Query("select coalesce(case when count(r)=1 then true else false end,false ) from RequestTeacher r where r.teacher.email = :email")
    boolean allowTeacher(@Param(value = "email") String email);

    @Query("""
                select
                new com.example.gradeassure.dto.response.RequestTeacherFindByResponse(
                            request.id,
                            request.teacher.email,
                            request.teacher.fullName
                )
                from RequestTeacher request
                where request.action = 1 and request.id = :requestId
            """)
    RequestTeacherFindByResponse findByIdRequestTeacherFindByResponse(@Param(value = "requestId") Long requestId);

    @Query("select test.name from RequestTeacher request join request.testTeachers test where request.id = :requestId")
    List<String> findAllTestName(@Param(value = "requestId") Long requestId);
}
// private Long id;
//    private String email;
//    private String fullName;
//    private Action action = Action.CHECK;