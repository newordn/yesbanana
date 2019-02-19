package com.derteuffel.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by derteuffel on 27/12/2018.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Education implements Serializable {

    @Id
    @GeneratedValue
    private Long educationId;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private int likes;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date realeseDate;
    @Autowired
    private Boolean status;
    @Column
    private ArrayList<String> pieces=new ArrayList<>();
    @Column
    private String type;
    @Column
    private String fileType;


    public Education() {
    }

    public Education(String title, String description, int likes, ArrayList<String> pieces, String type, Boolean status, Date realeseDate, String fileType) {
        this.title = title;
        this.description = description;
        this.likes = likes;
        this.realeseDate=realeseDate;
        this.pieces = pieces;
        this.type = type;
        this.status=status;
        this.fileType=fileType;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Long getEducationId() {
        return educationId;
    }

    public void setEducationId(Long educationId) {
        this.educationId = educationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public ArrayList<String> getPieces() {
        return pieces;
    }

    public void setPieces(ArrayList<String> pieces) {
        this.pieces = pieces;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getRealeseDate() {
        return realeseDate;
    }

    public void setRealeseDate(Date realeseDate) {
        this.realeseDate = realeseDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
