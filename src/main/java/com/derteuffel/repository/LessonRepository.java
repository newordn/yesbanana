package com.derteuffel.repository;

import com.derteuffel.data.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by derteuffel on 27/12/2018.
 */
@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long>{

    @Query("select l from Lesson as l join l.period lp where lp.periodId=:id order by l.lessonId desc")
    List<Lesson> findAllByPeriod(@Param("id") Long periodId);

    List<Lesson> findAllByStatusOrderByLessonIdDesc(Boolean status);
}
