package com.derteuffel.data;

import javax.persistence.*;
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
    private String faculty;

    @ManyToOne
    private University university;

    @OneToMany(mappedBy = "faculty")
    private List<Options> optionsList;

    public Faculty(String faculty) {
        this.faculty = faculty;
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

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }
}
