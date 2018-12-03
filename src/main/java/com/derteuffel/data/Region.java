package com.derteuffel.data;

import org.hibernate.validator.constraints.CodePointLength;

import javax.persistence.*;
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
    private String region;

    @ManyToOne
    private Country country;

    @OneToMany(mappedBy = "region")
    private List<University> universities;


    public Region(String region) {
        this.region = region;
    }

    public Region() {
    }

    public Long getRegionId() {
        return regionId;
    }

    public List<University> getUniversities() {
        return universities;
    }

    public void setUniversities(List<University> universities) {
        this.universities = universities;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
