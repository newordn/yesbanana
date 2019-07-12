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
    @Query("select b from Bibliography as b join b.user bu where bu.userId=:id order by b.bibliographyId desc")
    List<Bibliography> findAllByUser(@Param("id") Long userId);
    Page<Bibliography> findByMotCle(String motCle, Pageable pageable);
    List<Bibliography> findByMotCleAndDisponibility(String motCle,Boolean disponibility);
    Bibliography findByTitle(String title);
    List<Bibliography> findAllByTitle(String title);
    List<Bibliography> findAllByAuteur(String auteur);
    List<Bibliography> findAllByDisponibility(Boolean disponibility);
    @Query("select t from Bibliography as t where t.disponibility like :x and t.auteur like :y or t.title like :z or t.motCle like :a or t.description like :b or t.link like :c " +
            "or t.faculte like :d or t.options like :e or t.category like :f or t.editeur like :g or t.edition like :h or t.lieu_edition like :i or t.annee_edition like :j or t.isbnNumber like :k")
    List<Bibliography> rechercheB(@Param("x") Boolean disponibility,@Param("y") String auteur,@Param("z") String title,@Param("a") String motCle, @Param("b")String description, @Param("c")String link,
                          @Param("d")String faculte,@Param("e") String options,@Param("f") String category,@Param("g")String editeur, @Param("h")String edition, @Param("i") String lieu_edition,@Param("j") String annee_edition,@Param("k") String isbnNumber);

    Bibliography findByAuteur(String auteur);

    List<Bibliography> findByFaculteAndDisponibility(String faculte, Boolean disponibility);
    List<Bibliography> findByOptionsAndDisponibility(String options, Boolean disponibility);

}
