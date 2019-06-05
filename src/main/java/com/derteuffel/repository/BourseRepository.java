package com.derteuffel.repository;

import com.derteuffel.data.Bourse;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by derteuffel on 05/06/2019.
 */
@Repository
public interface BourseRepository extends JpaRepository<Bourse, Long> {
    List<Bourse> findByStatusAndSuprime(Boolean status,Boolean suprime);
    List<Bourse> findFirst12ByStatusAndSuprime(Boolean status, Boolean suprime, Sort sort);
    List<Bourse> findBySuprime(Boolean suprime);
}
