package com.derteuffel.data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by derteuffel on 13/12/2018.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Post implements Serializable {

    @Id
    @GeneratedValue
    @Column
    private Long postId;
    @Column
    private String title;
    private String promotion;
    private String faculty;
    private String publisherName;
    @Column
    private String description;
    @Column
    private String category;
    @Column
    private ArrayList<String> pieces;
    @Column
    private Date creationDate=new Date();
    private double publishPrice;
    private Boolean status;
    public Post() {
    }

    public Post(String title,Boolean status, double publishPrice,
                String description,String promotion, String faculty,
                String publisherName, String category, ArrayList<String> pieces, Date creationDate) {
        this.title = title;
        this.description = description;
        this.status=status;
        this.publishPrice=publishPrice;
        this.promotion= promotion;
        this.faculty=faculty;
        this.publisherName=publisherName;
        this.category = category;
        this.pieces = pieces;
        this.creationDate = creationDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public double getPublishPrice() {
        return publishPrice;
    }

    public void setPublishPrice(double publishPrice) {
        this.publishPrice = publishPrice;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<String> getPieces() {
        return pieces;
    }

    public void setPieces(ArrayList<String> pieces) {
        this.pieces = pieces;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
