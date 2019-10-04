package com.derteuffel.repository;

import com.derteuffel.data.Options;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by derteuffel on 02/12/2018.
 */
@Repository
public interface OptionsRepository extends JpaRepository<Options,Long> {

    @Query("select o from Options as o join o.faculty fo where fo.facultyId=:id order by o.optionsId desc")
    List<Options> findAllByFaculty(@Param("id") Long facultyId);
    Options findByOptionsName(String optionsName);
}
