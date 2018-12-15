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
public class Post implements Serializable {

    @Id
    @GeneratedValue
    @Column
    private Long postId;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private String category;
    @Column
    private ArrayList<String> pieces;
    @Column
    private Date creationDate=new Date();

    @ManyToOne
    private Region region;

    @ManyToOne
    private User user;

    public Post() {
    }

    public Post(String title,User user, String description, String category, ArrayList<String> pieces, Date creationDate, Region region) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.pieces = pieces;
        this.creationDate = creationDate;
        this.region = region;
        this.user=user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
}
