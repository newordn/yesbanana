package com.derteuffel.repository;

import com.derteuffel.data.Publicite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Repository
public interface PubliciteRepository  extends JpaRepository<Publicite, Long> {

    List<Publicite> findAllByStatus(Boolean status);
}
