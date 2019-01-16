package com.derteuffel.data;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by derteuffel on 27/12/2018.
 */
@Entity
public class Course implements Serializable {
    @Id
    @GeneratedValue
    private  Long courseId;
    @Column
    private String title;
    @Column
    private String tutor;
    @Column
    private double price;
    @Column
    private int views;
    @Column
    private  int notes;
    @Column
    private String description;
    @Column
    private String domain;

    @OneToMany(mappedBy = "course")
    @Cascade(CascadeType.ALL)
    private List<Period> periods;

    public Course() {
    }

    public Course(Long courseId, String title, String tutor, double price, int views, int notes, String description, String domain) {
        this.courseId = courseId;
        this.title = title;
        this.domain= domain;
        this.tutor = tutor;
        this.price = price;
        this.views = views;
        this.notes = notes;
        this.description = description;
    }


    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTutor() {
        return tutor;
    }

    public void setTutor(String tutor) {
        this.tutor = tutor;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getNotes() {
        return notes;
    }

    public void setNotes(int notes) {
        this.notes = notes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public void setPeriods(List<Period> periods) {
        this.periods = periods;
    }
}
