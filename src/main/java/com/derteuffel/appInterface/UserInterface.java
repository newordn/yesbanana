package com.derteuffel.appInterface;

import com.derteuffel.data.User;

/**
 * Created by derteuffel on 01/11/2018.
 */
public interface UserInterface extends crudInterface<User> {

    User findByEmail(String email);

    User update(User user);
}
