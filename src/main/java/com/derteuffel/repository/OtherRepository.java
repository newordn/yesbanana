package com.derteuffel.repository;

import com.derteuffel.data.Other;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by derteuffel on 27/12/2018.
 */
@Repository
public interface OtherRepository extends JpaRepository<Other, Long> {
}
