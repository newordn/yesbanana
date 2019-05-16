package com.derteuffel.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;

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
    @Cascade(CascadeType.ALL)
    private List<Event> events;
    @OneToMany(mappedBy = "region")
    @Cascade(CascadeType.ALL)
    private List<Education> educations;
    @OneToMany(mappedBy = "region")
    @Cascade(CascadeType.ALL)
    private List<Other> others;


    public Region(String regName,String chefLieu, Country country) {
        this.regName = regName;
        this.country=country;
        this.chefLieu=chefLieu;
    }

    public Region() {
    }

    @JsonIgnore
    public List<Education> getEducations() {
        return educations;
    }

    public void setEducations(List<Education> educations) {
        this.educations = educations;
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

    @JsonIgnore
    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
