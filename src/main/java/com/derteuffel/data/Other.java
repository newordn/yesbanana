package com.derteuffel.data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by derteuffel on 27/12/2018.
 */
@Entity
public class Other implements Serializable {
    @Id
    @GeneratedValue
    private Long otherId;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private Long likes;
    @Column
    private ArrayList<String> pieces=new ArrayList<>();
    @Column
    private String type;
    @ManyToOne
    private Region region;
    @ManyToOne
    private User user;

    public Other() {
    }

    public Other(String title, String description, Long likes, ArrayList<String> pieces, String type) {
        this.title = title;
        this.description = description;
        this.likes = likes;
        this.pieces = pieces;
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getOtherId() {
        return otherId;
    }

    public void setOtherId(Long otherId) {
        this.otherId = otherId;
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

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
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

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
}
