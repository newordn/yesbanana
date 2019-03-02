package com.derteuffel.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by derteuffel on 21/10/2018.
 */
@Entity
public class Groupe implements Serializable{

    @Id
    @GeneratedValue
    private Long groupeId;
    @Column
    private String groupeName;

    private String groupChief;
    private Boolean status;

    private String groupeCountry;
    private  String groupeRegion;

    @ManyToMany
    private List<User> users;

    @OneToMany(mappedBy = "groupe")
    @OnDelete(action= OnDeleteAction.NO_ACTION)
    private List<These> theses;

    public Groupe(String groupeName, String groupChief, String groupeRegion, String groupeCountry,Boolean status) {
        this.groupeName = groupeName;
        this.groupChief=groupChief;
        this.status=status;
        this.groupeCountry=groupeCountry;
        this.groupeRegion=groupeRegion;
    }
    public Groupe(String groupeName, String groupChief, String groupeRegion,Boolean status, String groupeCountry, List<User> users) {
        this.groupeName = groupeName;
        this.groupChief=groupChief;
        this.status=status;
        this.groupeCountry=groupeCountry;
        this.groupeRegion=groupeRegion;
        this.users=users;
    }

    public Groupe() {
    }

    public Boolean getStatus() {
        return status;
    }

    public void saveUsers(List<User> users){
        this.users=users;
    }
    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getGroupeId() {
        return groupeId;
    }

    public void setGroupeId(Long groupeId) {
        this.groupeId = groupeId;
    }

    public String getGroupeName() {
        return groupeName;
    }

    public void setGroupeName(String groupeName) {
        this.groupeName = groupeName;
    }
    @JsonIgnore
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(User user) {
               users.add(user);
    }

    @JsonIgnore
    public List<These> getTheses() {
        return theses;
    }

    public void setTheses(List<These> theses) {
        this.theses = theses;
    }

    public String getGroupChief() {
        return groupChief;
    }

    public void setGroupChief(String groupChief) {
        this.groupChief = groupChief;
    }

    public String getGroupeCountry() {
        return groupeCountry;
    }

    public void setGroupeCountry(String groupeCountry) {
        this.groupeCountry = groupeCountry;
    }

    public String getGroupeRegion() {
        return groupeRegion;
    }

    public void setGroupeRegion(String groupeRegion) {
        this.groupeRegion = groupeRegion;
    }
}
