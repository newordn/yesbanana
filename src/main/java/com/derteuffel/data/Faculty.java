package com.derteuffel.data;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.List;

/**
 * Created by derteuffel on 02/12/2018.
 */
@Entity
public class Faculty implements Serializable {

    @Id
    @GeneratedValue
    private Long facultyId;

    @Column
    private String facultyName;

    @ManyToOne
    private University university;

    @OneToMany(mappedBy = "faculty")
    @Cascade(CascadeType.ALL)
    private List<Options> optionsList;

    public Faculty(String facultyName, University university) {
        this.facultyName = facultyName;
        this.university=university;
    }

    public Faculty() {
    }

    public List<Options> getOptionsList() {
        return optionsList;
    }

    public void setOptionsList(List<Options> optionsList) {
        this.optionsList = optionsList;
    }

    public Long getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Long facultyId) {
        this.facultyId = facultyId;
    }


    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }
}
