package com.derteuffel.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.CodePointLength;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.List;

/**
 * Created by derteuffel on 02/12/2018.
 */
@Entity
public class Region implements Serializable {

    @Id
    @GeneratedValue
    private Long regionId;

    @Column
    private String regName;
    @Column
    private String chefLieu;

    @ManyToOne
    private Country country;

    @OneToMany(mappedBy = "region")
    @Cascade(CascadeType.ALL)
    private List<University> universities;
    @OneToMany(mappedBy = "region")
    private List<Post> posts;
    @OneToMany(mappedBy = "region")
    @OnDelete(action= OnDeleteAction.NO_ACTION)
    private List<Event> events;
    @OneToMany(mappedBy = "region")
    @OnDelete(action= OnDeleteAction.NO_ACTION)
    private List<Primaire> primaires;
    @OneToMany(mappedBy = "region")
    @OnDelete(action= OnDeleteAction.NO_ACTION)
    private List<Secondary> secondaries;
    @OneToMany(mappedBy = "region")
    @OnDelete(action= OnDeleteAction.NO_ACTION)
    private List<Other> others;


    public Region(String regName,String chefLieu, Country country) {
        this.regName = regName;
        this.country=country;
        this.chefLieu=chefLieu;
    }

    public Region() {
    }

    @JsonIgnore
    public List<Primaire> getPrimaires() {
        return primaires;
    }

    public void setPrimaires(List<Primaire> primaires) {
        this.primaires = primaires;
    }

    @JsonIgnore
    public List<Secondary> getSecondaries() {
        return secondaries;
    }

    public void setSecondaries(List<Secondary> secondaries) {
        this.secondaries = secondaries;
    }

    @JsonIgnore
    public List<Other> getOthers() {
        return others;
    }

    public void setOthers(List<Other> others) {
        this.others = others;
    }

    public String getChefLieu() {
        return chefLieu;
    }

    public void setChefLieu(String chefLieu) {
        this.chefLieu = chefLieu;
    }

    public Long getRegionId() {
        return regionId;
    }

    @JsonIgnore
    public List<University> getUniversities() {
        return universities;
    }

    public void setUniversities(List<University> universities) {
        this.universities = universities;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getRegName() {
        return regName;
    }

    public void setRegName(String regName) {
        this.regName = regName;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @JsonIgnore
    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
