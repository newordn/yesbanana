package com.derteuffel.data;

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
    private String country;

    @OneToMany(mappedBy = "country")
    private List<Region> regions;

    public Country(String country) {
        this.country = country;
    }

    public Country() {
    }

    public List<Region> getRegions() {
        return regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }
}
