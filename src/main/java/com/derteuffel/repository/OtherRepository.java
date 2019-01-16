package com.derteuffel.repository;

import com.derteuffel.data.Other;
import org.bouncycastle.asn1.esf.OtherRevRefs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by derteuffel on 27/12/2018.
 */
@Repository
public interface OtherRepository extends JpaRepository<Other, Long> {

    @Query("select o from Other as o join o.region ro where ro.regionId=:id order by o.otherId desc")
    List<Other> findAllByRegion(@Param("id") Long regionId);

    List<Other> findAllByType(String type);
}
