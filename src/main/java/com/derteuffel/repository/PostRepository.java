package com.derteuffel.repository;

import com.derteuffel.data.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by derteuffel on 13/12/2018.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
