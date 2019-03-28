package com.derteuffel.repository;

import com.derteuffel.data.Role;
import com.derteuffel.data.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by derteuffel on 06/10/2018.
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Collection<User> findAllByOrderByUserIdDesc();
    User findByEmail(String email);
    User findByName(String name);

    List<User> findAllByStatus(Boolean status);

    Page<User> findAllByActiveOrderByUserIdDesc(Boolean active,Pageable pageable);
    List<User> findAllByActiveOrderByUserIdDesc(Boolean active);
    List<User> findAllByDiplomOrderByUserIdDesc(String diplom);

    Page<User> findByGroupes_GroupeId( Long groupeId, Pageable pageable);
    List<User> findByGroupes_GroupeId( Long groupeId);
    //List<User> findByGroupes_GroupeId( Long groupeId, String category);
    Page<User> findAll(Pageable pageable);


    Set<User> findByRoles_Role(String role);

    List<User> findAllByCategory(String category);
    List<User> findAllByRegionOrderByUserIdDesc(String region);

    Optional<User> findByResetToken(String resetToken);


}
