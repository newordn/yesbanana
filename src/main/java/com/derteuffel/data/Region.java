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
    private String regName;
    @Column
    private String chefLieu;

    @ManyToOne
    private Country country;

    @OneToMany(mappedBy = "region")
    private List<University> universities;


    public Region(String regName,String chefLieu, Country country) {
        this.regName = regName;
        this.country=country;
        this.chefLieu=chefLieu;
    }

    public Region() {
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
}
