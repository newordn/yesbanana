package com.derteuffel.repository;

import com.derteuffel.data.Rapport;
import com.derteuffel.data.These;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by derteuffel on 01/06/2019.
 */
@Repository
public interface RapportRepository extends JpaRepository<Rapport,Long>{

    @Query("select r from Rapport as r join r.user ru where ru.userId=:id order by r.rapportId desc")
    List<Rapport> findByUser(@Param("id") Long userId);

    @Query("select r from Rapport as r join r.groupe rg where rg.groupeId=:id order by r.rapportId desc")
    List<Rapport> findByGroupe( @Param("id") Long groupeId);
}
