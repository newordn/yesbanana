package com.derteuffel.repository;

import com.derteuffel.data.Groupe;
import org.springframework.data.jpa.provider.PersistenceProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by derteuffel on 23/10/2018.
 */
@Repository
public interface GroupeRepository extends JpaRepository<Groupe, Long> {
    List<Groupe> findByUsers_UserId( Long userId);
}
