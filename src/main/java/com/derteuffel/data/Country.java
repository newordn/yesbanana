package com.derteuffel.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by derteuffel on 02/12/2018.
 */
@Entity
public class Country implements Serializable {

    @Id
    @GeneratedValue
    private Long countryId;
    @Column
    private String countryName;
    @Column
    private String countryStreet;

    @OneToMany(mappedBy = "country")
    private List<Region> regions;

    public Country(String countryName, String countryStreet) {
        this.countryName = countryName;
        this.countryStreet=countryStreet;
    }

    public Country() {
    }

    public String getCountryStreet() {
        return countryStreet;
    }

    public void setCountryStreet(String countryStreet) {
        this.countryStreet = countryStreet;
    }

    @JsonIgnore
    public List<Region> getRegions() {
        return regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }
}
