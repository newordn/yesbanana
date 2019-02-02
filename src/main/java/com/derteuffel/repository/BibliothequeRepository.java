package com.derteuffel.repository;

import com.derteuffel.data.Bibliotheque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by derteuffel on 01/02/2019.
 */
@Repository
public interface BibliothequeRepository extends JpaRepository<Bibliotheque,Long> {
    @Query("select b from Bibliotheque as b join b.these bt where bt.theseId=:id order by b.bibliothequeId desc")
    List<Bibliotheque> findAllByThese(@Param("id") Long theseId);
    Bibliotheque findByBibliotheques(String bibliotheques);
}
