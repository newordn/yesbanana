package com.derteuffel.repository;

import com.derteuffel.data.Role;
import com.derteuffel.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by derteuffel on 06/10/2018.
 */

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);

    List<User> findAllByActiveOrderByUserIdDesc(Boolean active);
    List<User> findAllByDiplomOrderByUserIdDesc(String diplom);

    List<User> findByGroupes_GroupeId( Long groupeId);
    //List<User> findByGroupes_GroupeId( Long groupeId, String category);


    @Query("select u from User as u join u.role ur where ur.role=:id order by u.userId desc")
    List<User> findAllByRole(@Param("id") String role);

    List<User> findAllByCategory(String category);

    Optional<User> findByResetToken(String resetToken);


}
