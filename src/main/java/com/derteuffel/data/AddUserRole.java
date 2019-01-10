package com.derteuffel.data;

import javax.mail.search.SearchTerm;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by derteuffel on 27/10/2018.
 */
public class AddUserRole {

    private Long userId;
    private Long roleId;

    private List<Role> roles;
    private User user;

    public AddUserRole() {
    }

    public AddUserRole(List<Role> roles, User user) {
        this.roles = roles;
        this.user = user;
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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
