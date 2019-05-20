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

    List<These> findAllByStatus(Boolean status);
    List<These> findAllByStates(Boolean states);
    @Query("select t from These as t where t.states like :x and t.motCle like :y")
    Page<These> findByStatesAndMotCle(@Param("x") Boolean states,@Param("y") String motCle, Pageable pageable);

    List<These> findAllByOptionsOrderByTheseIdDesc(String options);
    List<These> findAllByCountryOrderByTheseIdDesc(String country);
    List<These> findAllByRegionsOrderByTheseIdDesc(String regions);
    List<These> findAllByUniversityOrderByTheseIdDesc(String university);

    List<These> findAllByMotCle(String motCle);
}
