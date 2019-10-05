package com.derteuffel.data;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.DoubleSummaryStatistics;

/**
 * Created by derteuffel on 05/01/2019.
 */
@Entity
public class Event implements Serializable {

    @Id
    @GeneratedValue
    @Column
    private Long eventId;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private String type;
    @Column
    private String image;
    @Column
    private String category;
    @Column
    private ArrayList<String> pieces;
    @Column
    private Date creationDate=new Date();


    private int likes=0;

    private  Boolean status;



    public Event() {
    }

    public Event(String title,String type, String description,String image, Date creationDate,ArrayList<String> pieces,
                 int likes, Boolean status, String category) {
        this.title = title;
        this.status=status;
        this.category=category;
        this.type=type;
        this.description = description;
        this.image=image;
        this.pieces=pieces;
        this.creationDate = creationDate;
        this.likes = likes;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<String> getPieces() {
        return pieces;
    }

    public void setPieces(ArrayList<String> pieces) {
        this.pieces = pieces;
    }
}
