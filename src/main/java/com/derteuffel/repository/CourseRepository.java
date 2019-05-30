package com.derteuffel.repository;

import com.derteuffel.data.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by derteuffel on 27/12/2018.
 */
@Repository
public interface CourseRepository extends PagingAndSortingRepository<Course, Long> {
    Page<Course> findAllByDomainOrderByCourseIdDesc(String domain, Pageable pageable);

    List<Course> findFirst12ByDomain(String domain, Sort sort);

}
