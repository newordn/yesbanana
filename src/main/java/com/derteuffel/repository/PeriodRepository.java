package com.derteuffel.repository;

import com.derteuffel.data.Period;
import org.springframework.core.PrioritizedParameterNameDiscoverer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by derteuffel on 27/12/2018.
 */
@Repository
public interface PeriodRepository extends JpaRepository<Period,Long>{
    @Query("select p from Period as p join p.course pc where pc.courseId=:id order by p.periodId desc")
    List<Period> findAllByCourses(@Param("id") Long courseId);

    List<Period> findAllByStatusOrderByPeriodIdDesc(Boolean status);
}
