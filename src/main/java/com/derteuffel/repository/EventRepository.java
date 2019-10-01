package com.derteuffel.repository;

import com.derteuffel.data.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by derteuffel on 05/01/2019.
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("select e from Event as e where e.type=:type or e.title=:title or e.description=:description order by e.eventId desc")
    List<Event> findAllForSearch(@Param("title") String title, @Param("type") String type, @Param("description") String description);
    List<Event> findAllByType(String type);

}
