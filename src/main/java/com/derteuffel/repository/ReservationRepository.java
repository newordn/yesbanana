package com.derteuffel.repository;

import com.derteuffel.data.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by derteuffel on 16/06/2019.
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    @Query("select r from Reservation as r join r.colonie rc where rc.colonieId=:id order by r.reservationId desc")
    List<Reservation> findByColonie(Long colonieId);
}
