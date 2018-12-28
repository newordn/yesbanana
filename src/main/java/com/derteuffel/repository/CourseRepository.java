package com.derteuffel.repository;

import com.derteuffel.data.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by derteuffel on 27/12/2018.
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByDomainOrderByCourseIdDesc(String domain);
}
