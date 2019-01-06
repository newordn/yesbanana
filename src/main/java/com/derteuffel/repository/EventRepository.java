package com.derteuffel.repository;

import com.derteuffel.data.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by derteuffel on 05/01/2019.
 */
@Repository
public interface EventRepository extends PagingAndSortingRepository<Event, Long> {
    @Query("select e from Event as e join e.region er where er.regionId=:id order by e.eventId desc")
    List<Event> findAllByRegion(@Param("id") Long regionId);
    Page<Event> findAllByType(String type, Pageable pageable);
    List<Event> findAllByType(String type);
}
