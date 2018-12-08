package com.derteuffel.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by derteuffel on 14/10/2018.
 */
@Entity
public class These implements Serializable{

    @Id
    @GeneratedValue
    private Long theseId;
    @Column
    private String university;
    @Column
    private String faculty;
    @Column
    private String options;
    @Column
    private String level;
    @Column
    private String subject;
    @Column
    private String resumes;
    @Column
    private String regions;
    @Column
    private String student;
    @Column
    private String profesor;
    @Column
    private String workChief;
    @Column
    private String assistant;
    @Column
    private String bibliography;
    @Column
    private String library;
    @Column
    private Date created_at= new Date();
    @Column
    private String theseDate;
    @Column
    private String country;
    @Column
    private ArrayList<String> libraries;
    @Column
    private ArrayList<String> bibliographies;


    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne
    private Groupe groupe;
    public These() {
    }

    public These(String university, String faculty, String options, String level,
                 String subject, String resumes, String regions, String student, String profesor,
                 String workChief, String assistant, String bibliography, String library,
                 Date created_at, String theseDate, String country, ArrayList<String> libraries, ArrayList<String> bibliographies) {
        this.university = university;
        this.faculty = faculty;
        this.options = options;
        this.level = level;
        this.bibliographies=bibliographies;
        this.subject = subject;
        this.libraries=libraries;
        this.resumes = resumes;
        this.regions = regions;
        this.student = student;
        this.profesor = profesor;
        this.workChief = workChief;
        this.assistant = assistant;
        this.bibliography = bibliography;
        this.library = library;
        this.created_at = created_at;
        this.theseDate=theseDate;
        this.country=country;
    }

    public ArrayList<String> getLibraries() {
        return libraries;
    }

    public void setLibraries(ArrayList<String> libraries) {
        this.libraries = libraries;
    }

    public ArrayList<String> getBibliographies() {
        return bibliographies;
    }

    public void setBibliographies(ArrayList<String> bibliographies) {
        this.bibliographies = bibliographies;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getTheseId() {
        return theseId;
    }

    public void setTheseId(Long theseId) {
        this.theseId = theseId;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getResumes() {
        return resumes;
    }

    public void setResumes(String resumes) {
        this.resumes = resumes;
    }

    public String getRegions() {
        return regions;
    }

    public void setRegions(String regions) {
        this.regions = regions;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public String getWorkChief() {
        return workChief;
    }

    public void setWorkChief(String workChief) {
        this.workChief = workChief;
    }

    public String getAssistant() {
        return assistant;
    }

    public void setAssistant(String assistant) {
        this.assistant = assistant;
    }

    public String getBibliography() {
        return bibliography;
    }

    public void setBibliography(String bibliography) {
        this.bibliography = bibliography;
    }

    public String getLibrary() {
        return library;
    }

    public void setLibrary(String library) {
        this.library = library;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getTheseDate() {
        return theseDate;
    }

    public void setTheseDate(String theseDate) {
        this.theseDate = theseDate;
    }

    @Override
    public String toString() {
        return "These{" +
                "theseId=" + theseId +
                ", university='" + university + '\'' +
                ", faculty='" + faculty + '\'' +
                ", options='" + options + '\'' +
                ", level='" + level + '\'' +
                ", subject='" + subject + '\'' +
                ", resumes='" + resumes + '\'' +
                ", regions='" + regions + '\'' +
                ", student='" + student + '\'' +
                ", profesor='" + profesor + '\'' +
                ", workChief='" + workChief + '\'' +
                ", assistant='" + assistant + '\'' +
                ", bibliography=" + bibliography +
                ", library=" + library +
                ", created_at=" + created_at +
                '}';
    }
}
