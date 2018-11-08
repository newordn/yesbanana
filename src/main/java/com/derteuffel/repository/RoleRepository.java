package com.derteuffel.repository;

import com.derteuffel.data.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by derteuffel on 02/11/2018.
 */
@Repository
public interface RoleRepository extends CrudRepository<Role,Long> {
    Role findByRole(String role);
}
