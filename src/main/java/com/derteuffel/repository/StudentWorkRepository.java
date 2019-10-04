package com.derteuffel.repository;

import com.derteuffel.data.StudentWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by derteuffel on 19/04/2019.
 */
@Repository
public interface StudentWorkRepository extends JpaRepository<StudentWork,Long> {
    @Query("select s from StudentWork as s join s.these st where st.theseId=:id order by s.studentWorkId desc")
    List<StudentWork> findByThese(@Param("id") Long theseId);

    StudentWork findByDate(String date);

    List<StudentWork> findByStatusOrderByStudentWorkIdDesc(Boolean status);
}
