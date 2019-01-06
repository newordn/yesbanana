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
    private ArrayList<String> pieces;
    @Column
    private Date creationDate=new Date();

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column
    private Date releaseDate;
    @Column
    private Double price;

    private int likes=0;

    @ManyToOne
    private Region region;

    public Event() {
    }

    public Event(String title,String type, String description,String image, Date creationDate,ArrayList<String> pieces,
                 Date releaseDate, Double price, int likes, Region region) {
        this.title = title;
        this.type=type;
        this.description = description;
        this.image=image;
        this.pieces=pieces;
        this.creationDate = creationDate;
        this.releaseDate = releaseDate;
        this.price = price;
        this.likes = likes;
        this.region = region;
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

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
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
