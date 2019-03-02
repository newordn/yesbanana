package com.derteuffel.repository;

import com.derteuffel.data.AddUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by derteuffel on 28/02/2019.
 */
@Repository
public interface AddUserRoleRepository extends JpaRepository<AddUserRole,Long>{

    AddUserRole findByUserId(Long userId);
}
