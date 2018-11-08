package com.derteuffel.data;

import java.util.List;

/**
 * Created by derteuffel on 27/10/2018.
 */
public class AddUserGroupe {

    private Long userId;
    private Long groupeId;

    private List<Groupe> groupes;
    private User user;

    public AddUserGroupe() {
    }

    public AddUserGroupe(List<Groupe> groupes, User user) {
        this.groupes = groupes;
        this.user = user;
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

    public List<Groupe> getGroupes() {
        return groupes;
    }

    public void setGroupes(List<Groupe> groupes) {
        this.groupes = groupes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
