package com.derteuffel.repository;

import com.derteuffel.data.Entrepreneuriat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by derteuffel on 27/12/2018.
 */
@Repository
public interface EntrepreneuriatRepository extends JpaRepository<Entrepreneuriat, Long> {

    List<Entrepreneuriat> findAllByStatesAndStatus(Boolean states, Boolean status);

}
