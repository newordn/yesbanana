package com.derteuffel.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;
import java.util.Set;

/**
 * Created by derteuffel on 27/10/2018.
 */
@Entity
public class AddGroupeUser {
    @Id
    @GeneratedValue
    private Long addGroupeUserId;
    private Long userId;
    private Long groupeId;


    public AddGroupeUser() {
    }

    public AddGroupeUser(Long userId, Long groupeId) {
        this.userId = userId;

        this.groupeId = groupeId;
    }

    public Long getAddGroupeUserId() {
        return addGroupeUserId;
    }

    public void setAddGroupeUserId(Long addGroupeUserId) {
        this.addGroupeUserId = addGroupeUserId;
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

}
