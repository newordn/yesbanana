package com.derteuffel.repository;

import com.derteuffel.data.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by derteuffel on 02/12/2018.
 */
@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {

    @Query("select u from University as u join u.region ru where ru.regionId=:id order by u.universityId desc")
    List<University> findAllByRegion(@Param("id") Long regionId);
}
