package com.derteuffel.repository;

import com.derteuffel.data.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by derteuffel on 14/05/2019.
 */
@Repository
public interface LivreRepository extends JpaRepository<Post,Long> {

    List<Post> findAllByTypeAndSuprimeeOrderByPostIdDesc(String type, Boolean suprimee);
}
