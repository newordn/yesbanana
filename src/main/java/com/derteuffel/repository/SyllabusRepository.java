package com.derteuffel.repository;

import com.derteuffel.data.Syllabus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by derteuffel on 14/05/2019.
 */
@Repository
public interface SyllabusRepository extends JpaRepository<Syllabus,Long> {
    List<Syllabus> findBySuprimeeOrderBySyllabusIdDesc(Boolean suprimee);
    List<Syllabus> findBySuprimeeAndStatusOrderBySyllabusIdDesc(Boolean suprimee,Boolean status);
    @Query("select t from Syllabus as t where t.status like :x and t.auteur like :y or t.title like :z  or t.description like :b or t.category like :c or t.universite like :a or t.faculte like :d or t.options like :e or t.departement like :f or t.annee like :g or t.promotion like :h or t.otherInformation like :i order by t.syllabusId desc")
    List<Syllabus> rechercheS(@Param("x") Boolean status, @Param("y") String auteur, @Param("z") String title, @Param("a") String decription, @Param("b")String category, @Param("c")String universite,
                                  @Param("d")String faculte, @Param("e") String options, @Param("f") String departement, @Param("g")String annee, @Param("h")String promotion, @Param("i") String otherInformation);


}
