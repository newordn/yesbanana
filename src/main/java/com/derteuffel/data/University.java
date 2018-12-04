package com.derteuffel.data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by derteuffel on 02/12/2018.
 */
@Entity
public class University implements Serializable {

    @Id
    @GeneratedValue
    private Long universityId;

    @Column
    private String universityName;

    @ManyToOne
    private Region region;

    @OneToMany(mappedBy = "university")
    private List<Faculty> facultyList;

    public University(String universityName, Region region) {
        this.universityName = universityName;
        this.region = region;
    }

    public University() {
    }

    public List<Faculty> getFacultyList() {
        return facultyList;
    }

    public void setFacultyList(List<Faculty> facultyList) {
        this.facultyList = facultyList;
    }

    public Long getUniversityId() {
        return universityId;
    }

    public void setUniversityId(Long universityId) {
        this.universityId = universityId;
    }


    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
}
