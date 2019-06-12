package com.derteuffel.repository;

import com.derteuffel.data.These;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by derteuffel on 14/10/2018.
 */
@Repository
public interface TheseRepository extends JpaRepository<These,Long> {

    List<These> findAllByOrderByTheseIdDesc();
    List<These> findAllByFacultyOrderByTheseIdDesc(String faculty);

    @Query("select t from These as t join t.user tu where tu.userId=:id order by t.theseId desc")
    List<These> findByUserOrderByTheseIdDesc( @Param("id") Long userId);

    @Query("select t from These as t join t.groupe tg where tg.groupeId=:id order by t.theseId desc")
    List<These> findByGroupeOrderByTheseIdDesc( @Param("id") Long groupeId, Pageable pageable);

    @Query("select t from These as t join t.groupe tg where tg.groupeId=:id order by t.theseId desc")
    List<These> findByGroupeOrderByTheseIdDesc( @Param("id") Long groupeId);

    These findBySubject(String subject);
    These findByOptions(String options);

    List<These> findAllByStatus(Boolean status);
    List<These> findAllByStates(Boolean states);
    @Query("select t from These as t where t.states like :x and t.motCle like :y")
    Page<These> findByStatesAndMotCle(@Param("x") Boolean states,@Param("y") String motCle, Pageable pageable);

    @Query("select t from These as t where t.states like :x and t.motCle like :y")
    List<These> findStatesAndMotCle(@Param("x") Boolean states,@Param("y") String motCle);

    @Query("select t from These as t where t.states like :x and t.motCle like :y or t.student like :z or t.subject like :a or t.level like :b or t.options like :c " +
            "or t.university like :d or t.theseDate like :e or t.regions like :f or t.country like :g or t.profesor like :h")
    Page<These> recherche_avance(@Param("x") Boolean states,@Param("y") String motCle,@Param("z") String student,@Param("a") String subject, @Param("b")String level, @Param("c")String options,
                                 @Param("d")String university,@Param("e") String theseDate,@Param("f") String regions,@Param("g")String country, @Param("h")String profesor,Pageable pageable);

    @Query("select t from These as t where t.states like :x and t.motCle like :y or t.student like :z or t.subject like :a or t.level like :b or t.options like :c " +
            "or t.university like :d or t.theseDate like :e or t.regions like :f or t.country like :g or t.profesor like :h")
    List<These> recherche(@Param("x") Boolean states,@Param("y") String motCle,@Param("z") String student,@Param("a") String subject, @Param("b")String level, @Param("c")String options,
                                 @Param("d")String university,@Param("e") String theseDate,@Param("f") String regions,@Param("g")String country, @Param("h")String profesor);

    List<These> findAllByOptionsOrderByTheseIdDesc(String options);
    List<These> findAllByCountryOrderByTheseIdDesc(String country);
    List<These> findAllByRegionsOrderByTheseIdDesc(String regions);
    List<These> findAllByUniversityOrderByTheseIdDesc(String university);

    List<These> findAllByMotCle(String motCle);

}
