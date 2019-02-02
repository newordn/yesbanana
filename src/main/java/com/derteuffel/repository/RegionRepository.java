package com.derteuffel.repository;

import com.derteuffel.data.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by derteuffel on 02/12/2018.
 */
@Repository
public interface RegionRepository extends JpaRepository<Region, Long>{

    @Query("select r from Region as r join r.country cr where cr.countryId=:id order by r.regionId desc")
    List<Region> findAllByCountry(@Param("id") Long countryId);

    Region findByRegName(String regName);
}
