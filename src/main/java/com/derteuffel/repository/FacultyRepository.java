package com.derteuffel.repository;

import com.derteuffel.data.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by derteuffel on 02/12/2018.
 */
@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    @Query("select f from Faculty as f join f.university uf where uf.universityId=:id order by f.facultyId desc")
    List<Faculty> findAllByUnniversity(@Param("id") Long universityId);

    Faculty findByFacultyName(String facultyName);
}
