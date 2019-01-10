package com.derteuffel.repository;

import com.derteuffel.data.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

/**
 * Created by derteuffel on 02/11/2018.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByRole(String role);

    Set<Role> findByUsers_UserId(Long userId);
}
