package com.derteuffel.appInterface;

import com.derteuffel.data.User;

import java.util.List;
import java.util.Optional;

/**
 * Created by derteuffel on 01/11/2018.
 */
public interface UserInterface extends crudInterface<User> {

    User findByEmail(String email);

    User findByName(String name);

    User update(User user);

    List<User> findByCategory(String category);

    Optional<User> findByResetToken(String resetToken);

}
