package com.derteuffel.repository;

import com.derteuffel.data.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by derteuffel on 07/12/2018.
 */
@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
}
