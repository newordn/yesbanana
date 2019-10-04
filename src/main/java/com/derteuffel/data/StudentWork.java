package com.derteuffel.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by derteuffel on 19/04/2019.
 */
@Entity
public class StudentWork implements Serializable {
    @Id
    @GeneratedValue
    private Long studentWorkId;
    private String studentName;
    private String subject;
    private String date;
    private Boolean status;

    @ManyToOne
    private These these;

    public StudentWork() {
    }

    public StudentWork(String studentName,Boolean status, String subject, String date) {
        this.studentName = studentName;
        this.subject = subject;
        this.status=status;
        this.date = date;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @JsonIgnore
    public These getThese() {
        return these;
    }

    public void setThese(These these) {
        this.these = these;
    }

    public Long getStudentWorkId() {
        return studentWorkId;
    }

    public void setStudentWorkId(Long studentWorkId) {
        this.studentWorkId = studentWorkId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
