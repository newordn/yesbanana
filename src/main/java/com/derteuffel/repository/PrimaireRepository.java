package com.derteuffel.repository;

import com.derteuffel.data.Primaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by derteuffel on 27/12/2018.
 */
@Repository
public interface PrimaireRepository extends JpaRepository<Primaire, Long> {

    @Query("select p from Primaire as p join p.region pr where pr.regionId=:id order by p.educationId desc")
    List<Primaire> findAllByRegion(Long regionId);
}
