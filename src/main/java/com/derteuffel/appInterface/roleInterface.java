package com.derteuffel.appInterface;

import com.derteuffel.data.Role;

/**
 * Created by derteuffel on 01/11/2018.
 */
public interface roleInterface extends crudInterface<Role> {
    Role findByRole(String role);
}
