package com.derteuffel.repository;

import com.derteuffel.data.Matiere;
import org.hibernate.jpa.event.spi.jpa.Listener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatiereRepository extends JpaRepository<Matiere, Long> {
    List<Matiere> findByClasse(int classe);
    Matiere findByName(String name);
    List<Matiere> findAllByName(String name);
    Matiere findByNameAndClasse(String name, int classe);
}
