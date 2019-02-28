package com.derteuffel.data;

import javax.mail.search.SearchTerm;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by derteuffel on 27/10/2018.
 */
@Entity
public class AddUserRole implements Serializable{

    @Id
    @GeneratedValue
    private Long addUserRoleId;
    private Long userId;
    private Long roleId;

    public AddUserRole() {
    }

    public AddUserRole(Long userId, Long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getAddUserRoleId() {
        return addUserRoleId;
    }

    public void setAddUserRoleId(Long addUserRoleId) {
        this.addUserRoleId = addUserRoleId;
    }
}
