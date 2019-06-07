package com.derteuffel.repository;

import com.derteuffel.data.Colonie;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by derteuffel on 06/06/2019.
 */
@Repository
public interface ColonieRepository extends JpaRepository<Colonie,Long>{

List<Colonie> findFirst12ByCategoryAndActive(String category, Boolean active, Sort sort);
List<Colonie> findFirst12ByDateDebutAndActive(Date dateDebut, Boolean active, Sort sort);
List<Colonie> findFirst12ByDateFinAndActive(Date dateFin, Boolean active, Sort sort);
List<Colonie> findFirst12ByTypeAndActive(String type, Boolean active, Sort sort);
List<Colonie> findFirst12BySiteAndActive(String site, Boolean active, Sort sort);
List<Colonie> findFirst12ByPaysAndActive(String pays, Boolean active, Sort sort);
List<Colonie> findFirst12ByRegionAndActive(String region, Boolean active, Sort sort);
List<Colonie> findByStatus(Boolean status, Sort sort);
}
