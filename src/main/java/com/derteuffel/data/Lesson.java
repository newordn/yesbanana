package com.derteuffel.data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by derteuffel on 27/12/2018.
 */
@Entity
public class Lesson implements Serializable {
    @Id
    @GeneratedValue
    private Long lessonId;
    private Boolean status;
    private String title;
    @Column
    private ArrayList<String> pieces= new ArrayList<>();
    @Column
    private String resumes;
    @Column
    private String elapsedTime;
    @ManyToOne
    private Period period;

    public Lesson() {
    }

    public Lesson(String title, ArrayList<String> pieces, String resumes, String elapsedTime, Boolean status) {
        this.title = title;
        this.status=status;
        this.pieces = pieces;
        this.resumes = resumes;
        this.elapsedTime = elapsedTime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getPieces() {
        return pieces;
    }

    public void setPieces(ArrayList<String> pieces) {
        this.pieces = pieces;
    }

    public String getResumes() {
        return resumes;
    }

    public void setResumes(String resumes) {
        this.resumes = resumes;
    }

    public String getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(String elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

}
