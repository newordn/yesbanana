package com.derteuffel.repository;

import com.derteuffel.data.Panier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Neword on 30/03/2019.
 */
@Repository
public interface PanierRepository extends JpaRepository<Panier,Long> {
}
