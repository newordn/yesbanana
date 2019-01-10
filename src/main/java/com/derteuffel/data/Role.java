package com.derteuffel.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.derteuffel.data.User;
import org.springframework.stereotype.Controller;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by derteuffel on 20/10/2018.
 */
@Entity
public class Role {

 @Id
 @GeneratedValue
private Long roleId;

    private String role;

    @ManyToMany(mappedBy = "roles")
   private List<User> users;

    public Role() {
    }


    public Role(String role) {
        this.role = role;
    }
  /*  public void addUser(User user){
        if(!this.users.contains(user)){
            this.users.add(user);
        }

        if(!user.getRoles().contains(this)){
            user.getRoles().add(this);
        }
    }

    public void removeUser(User user){
        this.users.remove(user);
        user.getRoles().remove(this);
    }
*/

    @JsonIgnore
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(User user) {
        users.add(user);
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

   }
