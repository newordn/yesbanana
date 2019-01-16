package com.derteuffel.data;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by derteuffel on 27/12/2018.
 */
@Entity
public class Period implements Serializable {

    @Id
    @GeneratedValue
    private Long periodId;
    @Column
    private String title;
    @Column
    private String periodName;

    private Boolean status;

    @ManyToOne
    private Course course;
    @OneToMany(mappedBy = "period")
    @OnDelete(action= OnDeleteAction.NO_ACTION)
    private List<Lesson> lessons;

    public Period() {
    }

    public Period(Long periodId, String title, String periodName, Boolean status) {
        this.periodId = periodId;
        this.title = title;
        this.periodName = periodName;
        this.status=status;
    }

    public Long getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Long periodId) {
        this.periodId = periodId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
