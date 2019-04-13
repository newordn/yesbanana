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
    private Double price;
    @Column
    private ArrayList<String> pieces= new ArrayList<>();
    @Column
    private String resumes;
    @Column
    private String elapsedTime;
    private String fileType;
    @ManyToOne
    private Period period;

    public Lesson() {
    }

    public Lesson(String title, ArrayList<String> pieces, String resumes,Double price, String elapsedTime, Boolean status, String fileType) {
        this.title = title;
        this.status=status;
        this.pieces = pieces;
        this.fileType=fileType;
        this.resumes = resumes;
        this.price=price;
        this.elapsedTime = elapsedTime;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
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
