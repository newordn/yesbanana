package com.derteuffel.repository;

import com.derteuffel.data.AddGroupeUser;
import com.derteuffel.data.AddUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by derteuffel on 28/02/2019.
 */
@Repository
public interface AddGroupeUserRepository extends JpaRepository<AddGroupeUser,Long>{

    AddGroupeUser findByUserId(Long userId);
}
