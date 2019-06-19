package com.derteuffel.repository;

import com.derteuffel.data.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by derteuffel on 16/06/2019.
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    @Query("select r from Reservation as r join r.colonie rc where rc.colonieId=:id order by r.reservationId desc")
    List<Reservation> findByColonie(@Param("id") Long colonieId);

    @Query("select r from Reservation as r join r.colonie rc where rc.colonieId=:id and r.pays=:y and r.status=:x order by r.reservationId desc")
    List<Reservation> findByColonieAndStatusAndPays(@Param("id") Long colonieId,@Param("y") String pays, @Param("x") Boolean status);

    @Query("select r from Reservation as r join r.colonie rc where rc.colonieId=:id and r.region=:y and r.status=:x order by r.reservationId desc")
    List<Reservation> findByColonieAndStatusAndRegion(@Param("id") Long colonieId,@Param("y") String region, @Param("x") Boolean status);

    @Query("select r from Reservation as r join r.colonie rc where rc.colonieId=:id and r.site=:y and r.status=:x order by r.reservationId desc")
    List<Reservation> findByColonieAndStatusAndSite(@Param("id") Long colonieId,@Param("y") String site, @Param("x") Boolean status);

    @Query("select r from Reservation as r join r.colonie rc where rc.colonieId=:id and r.activite=:y and r.status=:x order by r.reservationId desc")
    List<Reservation> findByColonieAndStatusAndActivite(@Param("id") Long colonieId,@Param("y") String activite, @Param("x") Boolean status);

    @Query("select r from Reservation as r join r.colonie rc where rc.colonieId=:id and r.saison=:y and r.status=:x order by r.reservationId desc")
    List<Reservation> findByColonieAndStatusAndSaison(@Param("id") Long colonieId,@Param("y") String saison, @Param("x") Boolean status);

}
