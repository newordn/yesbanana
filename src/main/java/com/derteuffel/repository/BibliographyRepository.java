package com.derteuffel.repository;

import com.derteuffel.data.Bibliography;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by derteuffel on 30/01/2019.
 */
@Repository
public interface BibliographyRepository extends JpaRepository<Bibliography,Long> {

    @Query("select b from Bibliography as b join b.these bt where bt.theseId=:id order by b.bibliographyId desc")
    List<Bibliography> findAllByThese(@Param("id") Long theseId);
    @Query("select b from Bibliography as b join b.these bt where bt.theseId=:id and b.disponibility=:x order by b.bibliographyId desc")
    List<Bibliography> findAllByTheseAndDisponibility(@Param("id") Long theseId,@Param("x")Boolean disponibility);
    Page<Bibliography> findByMotCle(String motCle, Pageable pageable);
    List<Bibliography> findByMotCleAndDisponibility(String motCle,Boolean disponibility);
    Bibliography findByTitle(String title);
    List<Bibliography> findAllByTitle(String title);
    List<Bibliography> findAllByDisponibility(Boolean disponibility);
}
