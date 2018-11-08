package com.derteuffel.data;

import java.util.List;
import java.util.Set;

/**
 * Created by derteuffel on 27/10/2018.
 */
public class AddGroupeUser {
    private Long userId;
    private Long groupeId;


    List<User> users;
    Groupe groupe;

    public AddGroupeUser() {
    }

    public AddGroupeUser(List<User> users, Groupe groupe) {
        this.users = users;
        this.groupe = groupe;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGroupeId() {
        return groupeId;
    }

    public void setGroupeId(Long groupeId) {
        this.groupeId = groupeId;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }
}
