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
List<Colonie> findByCategoryAndStatusOrderByColonieIdDesc(String category, Boolean status);
List<Colonie> findFirst12ByDateDebutAndActive(Date dateDebut, Boolean active, Sort sort);
List<Colonie> findByDateDebutAndStatusOrderByColonieIdDesc(Date dateDebut, Boolean status);
List<Colonie> findFirst12ByDateFinAndActive(Date dateFin, Boolean active, Sort sort);
List<Colonie> findByDateFinAndStatusOrderByColonieIdDesc(Date dateFin, Boolean status);
List<Colonie> findFirst12ByTypeAndActive(String type, Boolean active, Sort sort);
List<Colonie> findByTypeAndStatusOrderByColonieIdDesc(String type, Boolean status);
List<Colonie> findFirst12BySiteAndActive(String site, Boolean active, Sort sort);
List<Colonie> findBySiteAndStatusOrderByColonieIdDesc(String site, Boolean status);
List<Colonie> findFirst12ByPaysAndActive(String pays, Boolean active, Sort sort);
List<Colonie> findByPaysAndStatusOrderByColonieIdDesc(String pays, Boolean status);
List<Colonie> findFirst12ByRegionAndActive(String region, Boolean active, Sort sort);
List<Colonie> findByRegionAndStatusOrderByColonieIdDesc(String region, Boolean status);
List<Colonie> findByStatus(Boolean status, Sort sort);
    List<Colonie> findFirst12ByActive(Boolean active, Sort sort);
    List<Colonie> findFirst3ByActive(Boolean active, Sort sort);
}
