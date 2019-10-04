package com.derteuffel.appInterface;

import com.derteuffel.data.Role;

import java.util.List;
import java.util.Set;

/**
 * Created by derteuffel on 01/11/2018.
 */
public interface roleInterface extends crudInterface<Role> {
    Role findByRole(String role);

    Set<Role> findByGroupe(Long userId);
}
